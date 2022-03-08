package com.saimawzc.freight.ui.my.setment.account.acountmanage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.account.MySetmentAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.SearchValueDto;
import com.saimawzc.freight.dto.account.MySetmentDto;
import com.saimawzc.freight.dto.account.MySetmentPageQueryDto;
import com.saimawzc.freight.presenter.mine.mysetment.MySetmentPresenter;
import com.saimawzc.freight.view.mine.setment.MySetmentView;
import com.saimawzc.freight.weight.utils.LoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.saimawzc.freight.adapter.BaseAdapter.IS_RESH;

/***
 *已确认结算单
 * **/
public class AlreadyOrderSetmentFragment extends BaseFragment implements MySetmentView {
    private MySetmentAdapter adapter;
    private List<MySetmentDto> mDatas=new ArrayList<>();
    @BindView(R.id.cy)
    RecyclerView rv;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private LoadMoreListener loadMoreListener;
    private int page=1;
    private MySetmentPresenter presenter;
    public List<SearchValueDto>searchValueDtos;
    private int checkstatus=3;
    @Override
    public int initContentView() {
        return R.layout.fragment_setlement_;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        adapter=new MySetmentAdapter(mDatas,mContext,2);
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(!IS_RESH){
                    page++;
                    presenter.getData(page,checkstatus,searchValueDtos);
                    IS_RESH=true;
                }
            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter=new MySetmentPresenter(this,mContext);
        presenter.getData(page,checkstatus,searchValueDtos);
        setNeedOnBus(true);
    }

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                if(searchValueDtos!=null){
                    searchValueDtos.clear();
                }
                presenter.getData(page,checkstatus,searchValueDtos);
            }
        });

    }


    @Override
    public void getMySetment(MySetmentPageQueryDto dtos) {
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }

        if(dtos.isLastPage()==false){
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }else {
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }
        adapter.addMoreData(dtos.getList());
    }

    @Override
    public void stopResh() {
        stopSwipeRefreshLayout(refreshLayout);
    }



    @Override
    public void showLoading() {
        context.showLoadingDialog();

    }

    @Override
    public void dissLoading() {
        context.dismissLoadingDialog();

    }

    @Override
    public void Toast(String str) {
        context.showMessage(str);

    }

    @Override
    public void oncomplete() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshShipPass(Intent intent) {
        if(intent!=null){
            String str=intent.getStringExtra("type");
            if(str.equals(Constants.reshAccount_confirm)){
                Log.e("msg","刷新"+str);
                page=1;
                searchValueDtos= (List<SearchValueDto>) intent.getSerializableExtra("list");
                presenter.getData(page,checkstatus,searchValueDtos);
            }
        }
    }
}

package com.saimawzc.freight.ui.my.setment.account.acountmanage;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
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
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.saimawzc.freight.adapter.BaseAdapter.IS_RESH;

/***
 * 未确认结算单
 * **/
public class WaitOrderSetmentFragment extends BaseFragment implements MySetmentView {


    private MySetmentAdapter adapter;
    private List<MySetmentDto> mDatas=new ArrayList<>();
    @BindView(R.id.cy)
    RecyclerView rv;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private LoadMoreListener loadMoreListener;
    private int page=1;
    private MySetmentPresenter presenter;
    private List<SearchValueDto>searchValueDtos;
    private int checkstatus=2;

    @Override
    public int initContentView() {
        return R.layout.fragment_setlement_;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        adapter=new MySetmentAdapter(mDatas,mContext,1);
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
    private NormalDialog dialog;
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

        adapter.setOnTabClickListener(new MySetmentAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type, final int position) {
                if(mDatas.size()<=position){
                    return;
                }
                dialog = new NormalDialog(mContext).isTitleShow(false)
                        .content("确定审核状态?")
                        .contentGravity(Gravity.CENTER)
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(2).btnText("同意", "拒绝");
                dialog.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isDestroy(context)){
                                    dialog.dismiss();
                                }
                                presenter.confirm(1,mDatas.get(position).getId());
                            }
                        },
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isDestroy(context)){
                                    dialog.dismiss();
                                }
                                presenter.confirm(2,mDatas.get(position).getId());

                            }
                        });
                dialog.show();
            }
        });
    }


    @Override
    public void getMySetment(MySetmentPageQueryDto dtos) {
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }
        if(dtos!=null){
            if(dtos.isLastPage()==false){
                loadMoreListener.isLoading = false;
                adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
            }else {
                loadMoreListener.isLoading = true;
                adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
            }
            adapter.addMoreData(dtos.getList());
        }

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
            if(str.equals(Constants.reshAccount_unconfirm)){
                Log.e("msg","刷新"+str);
                page=1;
                searchValueDtos= (List<SearchValueDto>) intent.getSerializableExtra("list");
                presenter.getData(page,checkstatus,searchValueDtos);
            }
        }
    }
}

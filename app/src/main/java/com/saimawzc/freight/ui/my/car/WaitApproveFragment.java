package com.saimawzc.freight.ui.my.car;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.my.SearchCarAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.my.car.MyCarDto;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.presenter.mine.car.MyCarPresenter;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.view.mine.car.MyCarView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.utils.LoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;



/**
 * Created by Administrator on 2020/8/1.
 */

public class WaitApproveFragment extends BaseFragment implements MyCarView, TextWatcher {

    @BindView(R.id.rv)RecyclerView rv;
    private SearchCarAdapter adapter;
    private List<SearchCarDto> mDatas=new ArrayList<>();
    private MyCarPresenter presenter;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    private int status=2; //checkStatus*	integer($int32)审核状态(1.已审核 3.审核中)
    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.edsearch) ClearTextEditText edSearch;
    @BindView(R.id.llSearch) LinearLayout llSearch;
    @BindView(R.id.tvSearch) TextView tvSearch;
    @Override
    public int initContentView() {
        return R.layout.fragment_unpass_car;
    }




    @Override
    public void initView() {
        mContext=getActivity();
        setNeedOnBus(true);
        presenter=new MyCarPresenter(this,mContext);
        adapter = new SearchCarAdapter(mDatas, mContext,2);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                isLoading = false;
                presenter.getcarList(status,page,edSearch.getText().toString());

            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter.getcarList(status,page,edSearch.getText().toString());
    }

    @Override
    public void initData() {
        edSearch.addTextChangedListener(this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getcarList(status,page,edSearch.getText().toString());
            }
        });

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("from","resistercar");
                bundle.putString("id",mDatas.get(position).getId());
                if(mDatas.get(position).getIfRegister()==1){
                    bundle.putBoolean("modify",true);
                }
                bundle.putString("type","carinfo");
                readyGo(PersonCenterActivity.class,bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page=1;
                presenter.getcarList(status,page,edSearch.getText().toString());
            }
        });
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
    @Override
    public void compelete(MyCarDto carDtoList) {
        llSearch.setVisibility(View.GONE);
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }
        if(carDtoList.isLastPage()==false){
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }else {
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);

        }
        adapter.addMoreData(carDtoList.getList());
    }



    @Override
    public void stopRefresh() {
        stopSwipeRefreshLayout(refreshLayout);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshManageorder(String str) {
        if(!TextUtils.isEmpty(str)){
            if(str.equals(Constants.reshCar)){
                Log.e("msg","刷新"+str);
                page=1;
                presenter.getcarList(status,page,edSearch.getText().toString());
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        //控制登录按钮是否可点击状态
        if (!TextUtils.isEmpty(edSearch.getText().toString())) {
            llSearch.setVisibility(View.VISIBLE);
            tvSearch.setText(edSearch.getText().toString());
        } else {
            llSearch.setVisibility(View.GONE);
        }
    }
}

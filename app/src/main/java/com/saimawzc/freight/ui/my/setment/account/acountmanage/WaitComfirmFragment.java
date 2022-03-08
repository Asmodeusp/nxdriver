package com.saimawzc.freight.ui.my.setment.account.acountmanage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.account.WaitComfirmAdpater;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.account.WaitComfirmAccountDto;
import com.saimawzc.freight.dto.account.WaitComfirmQueryPageDto;
import com.saimawzc.freight.presenter.mine.mysetment.WaitSetmentPresenter;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.view.mine.setment.WaitSetmentView;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 待结算运单
 * **/

public class WaitComfirmFragment extends BaseFragment implements WaitSetmentView {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rv) RecyclerView rv;
    private WaitComfirmAdpater adpater;
    private List<WaitComfirmAccountDto>mDatas=new ArrayList<>();
    private WaitSetmentPresenter presenter;
    private int page=1;
    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;
    private LoadMoreListener loadMoreListener;

    @Override
    public int initContentView() {
        return R.layout.fragment_waitconfirmaccount;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"待结算大单");
        adpater=new WaitComfirmAdpater(mDatas,mContext);
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adpater);
        presenter=new WaitSetmentPresenter(this,mContext);
        presenter.getData(page);

        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                isLoading = false;
                presenter.getData(page);

            }
        };
        rv.setOnScrollListener(loadMoreListener);
    }

    @Override
    public void initData() {
        adpater.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("from","waitdistapch");
                bundle.putString("id",mDatas.get(position).getPlanWayBillId());
                readyGo(OrderMainActivity.class,bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getData(page);
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
    public void getData(WaitComfirmQueryPageDto dto) {
        if(dto!=null){
            if(page==1){
                mDatas.clear();
                adpater.notifyDataSetChanged();
            }
            if(dto.isLastPage()==false){
                loadMoreListener.isLoading = false;
                adpater.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
            }else {
                loadMoreListener.isLoading = true;
                adpater.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
            }
            adpater.addMoreData(dto.getList());

        }
    }

    @Override
    public void stopResh() {
        stopSwipeRefreshLayout(refreshLayout);

    }
}

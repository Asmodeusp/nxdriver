package com.saimawzc.freight.ui.my.setment.account;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.account.ReconcilitionListAdpater;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.SearchValueDto;
import com.saimawzc.freight.dto.account.AccountQueryPageDto;
import com.saimawzc.freight.dto.account.ReconclitionDto;
import com.saimawzc.freight.presenter.mine.mysetment.AccountManagePresenter;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.view.mine.setment.AccountManageView;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/***
 * 对账列表
 * **/
public class ReconciliationListFragment extends BaseFragment implements AccountManageView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    private ReconcilitionListAdpater adpater;
    private List<ReconclitionDto>mDatas=new ArrayList<>();
    @BindView(R.id.rv) RecyclerView rv;
    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;

    private int page=1;
    private LoadMoreListener loadMoreListener;
    private AccountManagePresenter  presenter;
    @BindView(R.id.right_btn) TextView tvRightBtn;

    private List<SearchValueDto>searchValueDtos;

    public final int seachCode=115;

    @Override
    public int initContentView() {
        return R.layout.fragment_reconciliationlist;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"对账管理");

        adpater=new ReconcilitionListAdpater(mDatas,mContext);
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adpater);
        presenter=new AccountManagePresenter(this,mContext);

        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                isLoading = false;
                presenter.getData(page,searchValueDtos);

            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter.getData(page,searchValueDtos);
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
                bundle.putString("id",mDatas.get(position).getId());
                bundle.putString("from","reconciliondelation");
                readyGo(OrderMainActivity.class,bundle);

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        tvRightBtn.setText("查询");
        tvRightBtn.setVisibility(View.VISIBLE);
        tvRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Bundle bundle=new Bundle();
                 bundle.putString("from","reconciliation");
                readyGoForResult(OrderMainActivity.class,seachCode,bundle);

            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getData(page,searchValueDtos);
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
    public void getData(AccountQueryPageDto dtos) {
        if(page==1){
            mDatas.clear();
            adpater.notifyDataSetChanged();
        }
        if(dtos!=null){
            if(dtos.isLastPage()==false){
                loadMoreListener.isLoading = false;
                adpater.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
            }else {
                loadMoreListener.isLoading = true;
                adpater.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
            }
            adpater.addMoreData(dtos.getList());
        }

    }

    @Override
    public void stopRefresh() {
        stopSwipeRefreshLayout(refreshLayout);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==seachCode&& resultCode == RESULT_OK){
            if(searchValueDtos!=null){
                searchValueDtos.clear();
            }
             searchValueDtos = (List<SearchValueDto>) data.getSerializableExtra("list");
             page=1;
            presenter.getData(page,searchValueDtos);
        }


    }
}

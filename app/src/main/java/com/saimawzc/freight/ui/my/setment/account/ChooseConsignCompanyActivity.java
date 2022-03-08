package com.saimawzc.freight.ui.my.setment.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.account.ConsignCompanyAdapter;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.account.ConsignmentCompanyDto;
import com.saimawzc.freight.presenter.mine.mysetment.ConsignCompanyPresenter;
import com.saimawzc.freight.view.mine.setment.ConsignCompanyView;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2020/8/6.
 *
 * 选择托运公司
 */

public class ChooseConsignCompanyActivity extends BaseActivity
        implements ConsignCompanyView {

    @BindView(R.id.rv) RecyclerView rv;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private ConsignCompanyAdapter companyAdapter;
    private List<ConsignmentCompanyDto>mDatas=new ArrayList<>();
    private ConsignCompanyPresenter presenter;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Override
    protected int getViewId() {
        return R.layout.activity_chooseconsign;
    }
    @Override
    protected void init() {
        setToolbar(toolbar,"托运公司");
        companyAdapter=new ConsignCompanyAdapter(mDatas,this);
        LinearLayoutManager  layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(companyAdapter);
        presenter=new ConsignCompanyPresenter(this,this);
        presenter.getConsinList();
    }

    @Override
    protected void initListener() {
        companyAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent();
                intent.putExtra("data",mDatas.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getConsinList();
            }
        });
    }
    @Override
    protected void onGetBundle(Bundle bundle) {
    }
    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).fitsLayoutOverlapEnable(true).
                statusBarDarkFont(true).init();
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
    public void getCompany(List<ConsignmentCompanyDto> companyDtoList) {
        mDatas.clear();
        companyAdapter.addMoreData(companyDtoList);

    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);
    }
}

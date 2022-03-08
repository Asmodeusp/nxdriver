package com.saimawzc.freight.ui.sendcar.driver.error;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.order.MyErrorListAdpater;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.order.error.MyErrDto;
import com.saimawzc.freight.presenter.order.MyErrorReportPresenter;
import com.saimawzc.freight.view.order.error.MyErrorView;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/***
 * 我的异常上报
 * **/
public class MyErrorListFragment extends BaseFragment implements MyErrorView {
    @BindView(R.id.toolbar) Toolbar toolbar;
    private MyErrorReportPresenter presenter;
    @BindView(R.id.cv) RecyclerView rv;
    private List<MyErrDto>mDatas=new ArrayList<>();
    private MyErrorListAdpater adpater;


    @Override
    public int initContentView() {
        return R.layout.fragment_my_error;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"我的上报");
        presenter=new MyErrorReportPresenter(this,mContext);
        adpater=new MyErrorListAdpater(mDatas,mContext);
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adpater);
        presenter.getErrorType(getArguments().getString("id"));
    }

    @Override
    public void initData() {

    }

    @Override
    public void getErrorList(List<MyErrDto> myErrDtos) {
        adpater.addMoreData(myErrDtos);
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
}

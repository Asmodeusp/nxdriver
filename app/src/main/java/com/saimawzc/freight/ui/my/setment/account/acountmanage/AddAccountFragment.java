package com.saimawzc.freight.ui.my.setment.account.acountmanage;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.ui.order.OrderMainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加结算单
 * **/

public class AddAccountFragment extends BaseFragment {


    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @OnClick({R.id.btnsearch})
    public void click(View view){
        switch (view.getId()){
            case R.id.btnsearch:
                Bundle bundle=new Bundle();
                bundle.putString("from","waitaccount");
                readyGo(OrderMainActivity.class,bundle);
                break;
        }

    }


    @Override
    public int initContentView() {
        return R.layout.fragment_addaccount;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"添加结算单");

    }

    @Override
    public void initData() {

    }
}

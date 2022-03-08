package com.saimawzc.freight.ui.wallet;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.base.BaseImmersionFragment;
import com.saimawzc.freight.ui.my.PersonCenterActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MyWalletFragment extends BaseFragment {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    public int initContentView() {
        return R.layout.fragment_mywallet;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"我的钱包");
    }

    @Override
    public void initData() {
    }

    @OnClick(R.id.tvSign)
    public void click(){
        Bundle bundle=new Bundle();
        bundle.putString("from","sign");
        readyGo(PersonCenterActivity.class,bundle);
    }


}

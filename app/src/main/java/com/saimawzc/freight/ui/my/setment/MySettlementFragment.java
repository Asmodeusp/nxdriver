package com.saimawzc.freight.ui.my.setment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.ui.CommonActivity;
import com.saimawzc.freight.ui.order.OrderMainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/****
 * 我的结算
 * */
public class MySettlementFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int initContentView() {
        return R.layout.fragment_myselltlemet;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"我的结算");

    }

    @Override
    public void initData() {
    }
    @OnClick({R.id.rlduizhang,R.id.rlcount,R.id.rlfapiao,R.id.rlkoukuan})
    public void click(View view){
        Bundle bundle=new Bundle();
        switch (view.getId()){
            case R.id.rlduizhang://对账
                bundle.putString("from","reconciliationlist");
                readyGo(OrderMainActivity.class,bundle);
                break;
            case R.id.rlcount://结算
                bundle.putString("from","mycount");
                readyGo(OrderMainActivity.class,bundle);
                break;
            case R.id.rlfapiao:
                bundle.putString("title","发票管理");
                readyGo(CommonActivity.class,bundle);
                break;
            case R.id.rlkoukuan:
                bundle.putString("title","扣款管理");
                readyGo(CommonActivity.class,bundle);
                break;

        }
    }
}

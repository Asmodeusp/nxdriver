package com.saimawzc.freight.ui.order.waybill;

import android.support.v7.widget.Toolbar;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import butterknife.BindView;

public class OrderSubcontractFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int initContentView() {
        return R.layout.fragment_subcontract;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"转包");

    }

    @Override
    public void initData() {

    }
}

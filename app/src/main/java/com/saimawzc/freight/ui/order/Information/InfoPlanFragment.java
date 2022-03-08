package com.saimawzc.freight.ui.order.Information;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.order.OrderInfoBiddAdpater;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.order.OrderInfoDto;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *计划单
 * */

public class InfoPlanFragment extends BaseFragment {

    @BindView(R.id.rv) RecyclerView rv;
    private OrderInfoBiddAdpater adpater;
    private List<OrderInfoDto>mDatas=new ArrayList<>();

    @Override
    public int initContentView() {
        return R.layout.fragment_infoplan;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        for(int i=0;i<4;i++){
            OrderInfoDto dto=new OrderInfoDto();
            dto.setUserName("宁");
            dto.setGoodsName("dsf");
            dto.setGoodsNum("5");
            mDatas.add(dto);
        }
        adpater=new OrderInfoBiddAdpater(mDatas,mContext);
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adpater);

    }

    @Override
    public void initData() {

    }
}

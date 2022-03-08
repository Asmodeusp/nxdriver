package com.saimawzc.freight.ui.sendcar;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.order.SendCarAdapter;
import com.saimawzc.freight.adapter.sendcar.CancelOrderListAdpater;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.order.CancelOrderDto;
import com.saimawzc.freight.presenter.sendcar.CancelOrderListPresenter;
import com.saimawzc.freight.view.order.CancelOrderListView;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/****
 * 同意撤单
 * ***/
public class AgreenOrderFragment extends BaseFragment implements CancelOrderListView {

    @BindView(R.id.cycle) RecyclerView rv;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private List<CancelOrderDto>mDatas=new ArrayList<>();
    private CancelOrderListPresenter presenter;
    private String id;
    private CancelOrderListAdpater adapter;

    @Override
    public int initContentView() {
        return R.layout.fragment_agreenorder;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"同意撤单");
        presenter=new CancelOrderListPresenter(this,mContext);
        id=getArguments().getString("id");
        if(!TextUtils.isEmpty(id)){
            presenter.getData(id);
            adapter=new CancelOrderListAdpater(mDatas,mContext);
            layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            rv.setLayoutManager(layoutManager);
            rv.setAdapter(adapter);
        }

    }

    @Override
    public void initData() {
        adapter.setOnTabClickListener(new BaseAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type, int position) {
                if(type.equals("viewrefuse")){
                    presenter.sh(mDatas.get(position).getId(),2);

                }else if(type.equals("viewagreen")){
                    presenter.sh(mDatas.get(position).getId(),1);
                }

            }
        });

    }

    @Override
    public void getList(List<CancelOrderDto> dtos) {
        if(dtos!=null){
            adapter.addMoreData(dtos);
        }

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
        context.finish();
    }
}

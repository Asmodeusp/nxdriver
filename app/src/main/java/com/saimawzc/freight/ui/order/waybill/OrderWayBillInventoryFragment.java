package com.saimawzc.freight.ui.order.waybill;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.order.OrderInventoryAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.order.OrderInventoryDto;
import com.saimawzc.freight.dto.order.bill.WayBillDto;
import com.saimawzc.freight.dto.order.mainindex.RobOrderDto;
import com.saimawzc.freight.dto.order.mainindex.WaitOrderDto;
import com.saimawzc.freight.presenter.order.waybill.WayBillInventoryPresenter;
import com.saimawzc.freight.view.order.WayBillInventoryView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/***
 * 预运单清单
 * **/

public class OrderWayBillInventoryFragment extends BaseFragment implements WayBillInventoryView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.cy) RecyclerView rv;
    private OrderInventoryAdapter adapter;
    private List<OrderInventoryDto.qdData> mDatas=new ArrayList<>();
    private WaitOrderDto.waitOrderData data;
    WayBillDto.OrderBillData waybillData;
    @BindView(R.id.tvSendAdress) TextView tvSendAdress;
    @BindView(R.id.tvReceiveAdress)TextView tvReceiveAdress;
    private WayBillInventoryPresenter presenter;
    private String type;

    RobOrderDto.robOrderData robData;
    @Override
    public int initContentView() {
        return R.layout.fragment_waybillinventory;
    }

    @Override
    public void initView() {

        mContext=getContext();
        context.setToolbar(toolbar,"清单");
        type=getArguments().getString("type");
        try {
            if(type.equals("wait")){
                data= (WaitOrderDto.waitOrderData) getArguments().getSerializable("data");
            }else if(type.equals("rob")){
                robData= (RobOrderDto.robOrderData) getArguments().getSerializable("data");
            }else if(type.equals("waybill")){
                waybillData= (WayBillDto.OrderBillData) getArguments().getSerializable("data");
            }


        }catch (Exception e){

        }
        adapter = new OrderInventoryAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        if(data!=null){
            tvSendAdress.setText(data.getFromProName());
            tvReceiveAdress.setText(data.getToProName());
            presenter=new WayBillInventoryPresenter(this,mContext);
            presenter.getInventoryList(data.getWaybillId());
        }else if(robData!=null){
            tvSendAdress.setText(robData.getFromProName());
            tvReceiveAdress.setText(robData.getToProName());
            presenter=new WayBillInventoryPresenter(this,mContext);
            presenter.getInventoryList(robData.getWaybillId());
        }else if(waybillData!=null){
            tvSendAdress.setText(waybillData.getFromProName());
            tvReceiveAdress.setText(waybillData.getToProName());
            presenter=new WayBillInventoryPresenter(this,mContext);
            presenter.getInventoryList(waybillData.getId());
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void getInventoryList(List<OrderInventoryDto.qdData> dtos) {
        adapter.addMoreData(dtos);

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

package com.saimawzc.freight.presenter.order.waybill;

import android.content.Context;

import com.saimawzc.freight.dto.order.OrderInventoryDto;
import com.saimawzc.freight.modle.order.modelImple.WayBillInventoryModelImple;
import com.saimawzc.freight.modle.order.modle.WayBillInventoryModel;
import com.saimawzc.freight.view.order.WayBillInventoryView;
import com.saimawzc.freight.weight.utils.listen.order.WayBillInventoryListener;

import java.util.List;

/**
 * 预运单清单
 * **/
public class WayBillInventoryPresenter implements WayBillInventoryListener {


    private Context mContext;
    WayBillInventoryModel model;
    WayBillInventoryView view;

    public WayBillInventoryPresenter(WayBillInventoryView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new WayBillInventoryModelImple();
    }

    public void getInventoryList(String id){
        model.getWayBillList(view,this,id);
    }




    @Override
    public void successful() {
        view.oncomplete();


    }

    @Override
    public void onFail(String str) {
    view.Toast(str);
    }

    @Override
    public void successful(int type) {

    }


    @Override
    public void back(List<OrderInventoryDto.qdData> dtos) {
        view.getInventoryList(dtos);

    }
}

package com.saimawzc.freight.presenter.manage;

import android.content.Context;

import com.saimawzc.freight.dto.order.OrderManageRoleDto;
import com.saimawzc.freight.modle.order.modelImple.ManagwMapModelImple;
import com.saimawzc.freight.modle.order.modle.ManagwMapModel;
import com.saimawzc.freight.view.order.OrderManageMapView;
import com.saimawzc.freight.weight.utils.listen.order.OrderManageMapListener;

import java.util.List;

public class OrderManageMapPresenter implements OrderManageMapListener {

    private Context mContext;
    ManagwMapModel model;
    OrderManageMapView view;

    public OrderManageMapPresenter(OrderManageMapView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ManagwMapModelImple();
    }

    public void getcarrive(String id){
        model.getOrderManageList(view,this, id);
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
    public void back(OrderManageRoleDto dtos) {
        view.getList(dtos);
    }
}

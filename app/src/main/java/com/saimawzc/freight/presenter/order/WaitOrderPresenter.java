package com.saimawzc.freight.presenter.order;

import android.content.Context;

import com.saimawzc.freight.dto.order.bill.MyPlanOrderDto;
import com.saimawzc.freight.dto.order.mainindex.WaitOrderDto;
import com.saimawzc.freight.modle.order.modelImple.PlanOrderModelImple;
import com.saimawzc.freight.modle.order.modelImple.WaitOrderModelImple;
import com.saimawzc.freight.modle.order.modle.PlanOrderModel;
import com.saimawzc.freight.modle.order.modle.WaitOrderModel;
import com.saimawzc.freight.view.order.PlanOrderView;
import com.saimawzc.freight.view.order.WaitOrderView;
import com.saimawzc.freight.weight.utils.listen.order.PlanOrderListener;
import com.saimawzc.freight.weight.utils.listen.order.WaitOrderListener;

import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */

public class WaitOrderPresenter implements WaitOrderListener {

    private Context mContext;
    WaitOrderView view;
    WaitOrderModel model;
    public WaitOrderPresenter(WaitOrderView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new WaitOrderModelImple() ;
    }

    public void getData(int page){
        model.getCarList(view,this,page);
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
    public void waitordetList(List<WaitOrderDto.waitOrderData> dtos) {
        view.getPlanOrderList(dtos);
    }
}

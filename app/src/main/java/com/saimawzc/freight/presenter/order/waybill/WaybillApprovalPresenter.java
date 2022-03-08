package com.saimawzc.freight.presenter.order.waybill;

import android.content.Context;

import com.saimawzc.freight.dto.order.OrderDelationDto;
import com.saimawzc.freight.modle.order.modelImple.WayBillApprovalModelImple;
import com.saimawzc.freight.modle.order.modle.WayBillApprovalModel;
import com.saimawzc.freight.view.order.WaybillApprovalView;
import com.saimawzc.freight.weight.utils.listen.order.OrderDelationListener;


/****
 * 订单审核
 * **/
public class WaybillApprovalPresenter implements OrderDelationListener {

    private Context mContext;
    WayBillApprovalModel model;
    WaybillApprovalView view;

    public WaybillApprovalPresenter(WaybillApprovalView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new WayBillApprovalModelImple();
    }
    public void getpOrderDelation(String id,String type){
        model.getOrderDelation(view,this,id,type);
    }
    public void getSjOrderDelation(String id,String type){
        model.getsjOrderDelation(view,this,id,type);
    }

    public  void approval(String id,int status,String type){
        model.approval(view,this,id,status,type);
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
    public void back(OrderDelationDto dtos) {
        view.getOrderDelation(dtos);

    }
}

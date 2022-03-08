package com.saimawzc.freight.presenter.order.waybill;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.order.AddWayBillDto;
import com.saimawzc.freight.dto.order.OrderDelationDto;
import com.saimawzc.freight.modle.order.modelImple.AddWayBillModelImple;
import com.saimawzc.freight.modle.order.modelImple.WayBillApprovalModelImple;
import com.saimawzc.freight.modle.order.modle.AddWayBillModel;
import com.saimawzc.freight.modle.order.modle.WayBillApprovalModel;
import com.saimawzc.freight.view.order.AddwaybillView;
import com.saimawzc.freight.view.order.WaybillApprovalView;
import com.saimawzc.freight.weight.utils.listen.order.OrderDelationListener;

import java.util.List;


/****
 * 订单审核
 * **/
public class AddWayBillPresenter implements BaseListener {

    private Context mContext;
    AddWayBillModel model;
    AddwaybillView view;

    public AddWayBillPresenter(AddwaybillView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new AddWayBillModelImple();
    }
    public void addWayBill(String id, List<AddWayBillDto>dtos){
        model.addWayBill(view,this,id,dtos);
    }

    public void addsjWayBill(String id, List<AddWayBillDto>dtos){
        model.addsjWayBill(view,this,id,dtos);
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

}

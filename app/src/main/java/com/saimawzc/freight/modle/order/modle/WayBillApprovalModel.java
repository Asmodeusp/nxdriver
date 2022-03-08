package com.saimawzc.freight.modle.order.modle;


import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.view.order.WaybillApprovalView;
import com.saimawzc.freight.weight.utils.listen.order.OrderDelationListener;

/***
 * 审核
 * **/
public interface WayBillApprovalModel {
    void approval(WaybillApprovalView view, BaseListener listener, String id, int status,String type);
    void getOrderDelation(WaybillApprovalView view, OrderDelationListener listener, String id,String type);
    void getsjOrderDelation(WaybillApprovalView view, OrderDelationListener listener, String id,String type);

}

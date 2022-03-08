package com.saimawzc.freight.view.order;


import com.saimawzc.freight.dto.order.OrderDelationDto;
import com.saimawzc.freight.view.BaseView;

/***
 * 订单审核
 * */
public interface WaybillApprovalView extends BaseView {

    void getOrderDelation(OrderDelationDto dto);
}

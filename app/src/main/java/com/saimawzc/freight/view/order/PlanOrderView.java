package com.saimawzc.freight.view.order;

import com.saimawzc.freight.dto.order.PlanOrderReshDto;
import com.saimawzc.freight.dto.order.bill.MyPlanOrderDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface PlanOrderView extends BaseView {

    void getPlanOrderList(List<MyPlanOrderDto.planOrderData>dtos);
    void stopResh();
    void isLastPage(boolean isLastPage);
    void reshPlanData(PlanOrderReshDto dto,int position);
}

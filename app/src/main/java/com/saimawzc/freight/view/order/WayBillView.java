package com.saimawzc.freight.view.order;

import com.saimawzc.freight.dto.order.bill.WayBillDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface WayBillView extends BaseView {

    void getPlanOrderList(List<WayBillDto.OrderBillData> dtos);
    void stopResh();
    void isLastPage(boolean isLastPage);
}

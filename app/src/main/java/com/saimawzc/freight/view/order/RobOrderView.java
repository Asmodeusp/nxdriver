package com.saimawzc.freight.view.order;

import com.saimawzc.freight.dto.order.mainindex.RobOrderDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface RobOrderView extends BaseView {
    void getPlanOrderList(List<RobOrderDto.robOrderData> dtos);
    void stopResh();
    void isLastPage(boolean isLastPage);
}

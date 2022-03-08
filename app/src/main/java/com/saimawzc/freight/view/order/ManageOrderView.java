package com.saimawzc.freight.view.order;

import com.saimawzc.freight.dto.order.ManageListDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface ManageOrderView extends BaseView {
    void getPlanOrderList(List<ManageListDto.ManageOrderData> dtos);
    void stopResh();
    void isLastPage(boolean isLastPage);
}

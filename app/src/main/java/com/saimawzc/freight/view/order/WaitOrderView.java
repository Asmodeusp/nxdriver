package com.saimawzc.freight.view.order;

import com.saimawzc.freight.dto.order.mainindex.WaitOrderDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface WaitOrderView extends BaseView {

    void getPlanOrderList(List<WaitOrderDto.waitOrderData> dtos);
    void stopResh();
    void isLastPage(boolean isLastPage);
}

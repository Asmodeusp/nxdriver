package com.saimawzc.freight.view.order;

import com.saimawzc.freight.dto.order.CancelOrderDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface CancelOrderListView extends BaseView {
    void getList(List<CancelOrderDto>dtos);
}

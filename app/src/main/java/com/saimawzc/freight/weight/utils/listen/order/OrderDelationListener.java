package com.saimawzc.freight.weight.utils.listen.order;


import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.order.OrderDelationDto;

public interface OrderDelationListener extends BaseListener {

    void back(OrderDelationDto dtos);
}

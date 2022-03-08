package com.saimawzc.freight.weight.utils.listen.order;


import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.order.CarModelDto;
import com.saimawzc.freight.dto.order.OrderDelationDto;

import java.util.List;

public interface SendCarModellListen extends BaseListener {

    void back(List<CarModelDto> dtos);
}

package com.saimawzc.freight.weight.utils.listen.order;


import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.order.CarInfolDto;
import com.saimawzc.freight.dto.order.CarModelDto;

import java.util.List;

public interface SendCarInfolListen extends BaseListener {

    void backinfo(List<CarInfolDto.carInfoData> dtos);
}

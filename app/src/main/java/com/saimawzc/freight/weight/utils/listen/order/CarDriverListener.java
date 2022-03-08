package com.saimawzc.freight.weight.utils.listen.order;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.order.CarDriverDto;
import com.saimawzc.freight.dto.order.ManageListDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 *
 */

public interface CarDriverListener extends BaseListener {


    void getManageOrderList(List<CarDriverDto> dtos);

}

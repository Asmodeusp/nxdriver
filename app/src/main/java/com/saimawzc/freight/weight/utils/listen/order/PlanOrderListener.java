package com.saimawzc.freight.weight.utils.listen.order;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.order.bill.MyPlanOrderDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 *
 */

public interface PlanOrderListener extends BaseListener {


    void planOrderList(List<MyPlanOrderDto.planOrderData> dtos);

}

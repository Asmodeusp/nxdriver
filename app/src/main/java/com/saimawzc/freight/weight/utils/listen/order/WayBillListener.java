package com.saimawzc.freight.weight.utils.listen.order;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.order.bill.MyPlanOrderDto;
import com.saimawzc.freight.dto.order.bill.WayBillDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 *
 */

public interface WayBillListener extends BaseListener {


    void planOrderList(List<WayBillDto.OrderBillData> dtos);

}

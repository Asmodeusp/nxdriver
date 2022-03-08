package com.saimawzc.freight.weight.utils.listen.order;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.order.bill.MyPlanOrderDto;
import com.saimawzc.freight.dto.order.mainindex.WaitOrderDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 *
 */

public interface WaitOrderListener extends BaseListener {


    void waitordetList(List<WaitOrderDto.waitOrderData> dtos);

}

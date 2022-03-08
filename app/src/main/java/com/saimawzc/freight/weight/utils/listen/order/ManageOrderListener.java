package com.saimawzc.freight.weight.utils.listen.order;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.order.ManageListDto;
import com.saimawzc.freight.dto.order.bill.MyPlanOrderDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 *
 */

public interface ManageOrderListener extends BaseListener {


    void getManageOrderList(List<ManageListDto.ManageOrderData> dtos);

}

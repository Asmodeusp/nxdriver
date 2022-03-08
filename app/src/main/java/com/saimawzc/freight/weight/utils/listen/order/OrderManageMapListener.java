package com.saimawzc.freight.weight.utils.listen.order;



import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.order.OrderManageRoleDto;

import java.util.List;

public interface OrderManageMapListener extends BaseListener {

    void back(OrderManageRoleDto dtos);
}

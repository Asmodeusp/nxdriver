package com.saimawzc.freight.view.order;



import com.saimawzc.freight.dto.order.OrderManageRoleDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface OrderManageMapView extends BaseView {
    void getList(OrderManageRoleDto dtos);
}

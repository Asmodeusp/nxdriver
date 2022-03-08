package com.saimawzc.freight.modle.order.modle;


import com.saimawzc.freight.view.order.OrderManageMapView;
import com.saimawzc.freight.weight.utils.listen.order.OrderManageMapListener;

public interface ManagwMapModel {
    void getOrderManageList(OrderManageMapView view,
                            OrderManageMapListener listener, String id);
}

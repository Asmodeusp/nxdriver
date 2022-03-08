package com.saimawzc.freight.modle.order.modle;

import com.saimawzc.freight.view.order.ManageOrderView;
import com.saimawzc.freight.view.order.RobOrderView;
import com.saimawzc.freight.weight.utils.listen.order.ManageOrderListener;
import com.saimawzc.freight.weight.utils.listen.order.RobOrderListener;

public interface RobOrderModel {
    void getRobLsit(RobOrderView view, RobOrderListener listener,
                    int page);
}

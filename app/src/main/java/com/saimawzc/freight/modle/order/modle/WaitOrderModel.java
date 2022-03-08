package com.saimawzc.freight.modle.order.modle;

import com.saimawzc.freight.view.order.WaitOrderView;
import com.saimawzc.freight.weight.utils.listen.order.PlanOrderListener;
import com.saimawzc.freight.weight.utils.listen.order.WaitOrderListener;

public interface WaitOrderModel {

    void getCarList(WaitOrderView view, WaitOrderListener listener,
                    int page);
}

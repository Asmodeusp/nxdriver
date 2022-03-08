package com.saimawzc.freight.modle.order.modle.bidd;
import com.saimawzc.freight.view.order.error.CancelOrderView;

public interface CancelOrderModel {
    void submitError(CancelOrderView view, String id, String reason);
}

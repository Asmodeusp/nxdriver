package com.saimawzc.freight.modle.order.modle;


import com.saimawzc.freight.view.order.WayBillInventoryView;
import com.saimawzc.freight.weight.utils.listen.order.WayBillInventoryListener;

/***
 * 预运单清单
 * **/
public interface WayBillInventoryModel {
    void getWayBillList(WayBillInventoryView view, WayBillInventoryListener listListener, String id);

}

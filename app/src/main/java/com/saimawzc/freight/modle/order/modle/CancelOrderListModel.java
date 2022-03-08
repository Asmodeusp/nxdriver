package com.saimawzc.freight.modle.order.modle;


import com.saimawzc.freight.view.order.CancelOrderListView;

public interface CancelOrderListModel {
    void getListData(CancelOrderListView view, String id);

    void shOrder(CancelOrderListView view,String id,int Status);
}

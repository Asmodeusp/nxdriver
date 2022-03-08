package com.saimawzc.freight.presenter.sendcar;

import android.content.Context;

import com.saimawzc.freight.modle.order.CannelOrderListModelImple;
import com.saimawzc.freight.modle.order.modle.CancelOrderListModel;
import com.saimawzc.freight.modle.sendcar.imple.LogistoicModelImple;
import com.saimawzc.freight.modle.sendcar.model.LogistocModel;
import com.saimawzc.freight.view.order.CancelOrderListView;
import com.saimawzc.freight.view.sendcar.LogistoicView;

/**
 * Created by Administrator on 2020/7/30.
 */

public class CancelOrderListPresenter {

    private Context mContext;
    CancelOrderListView view;
    CancelOrderListModel model;
    public CancelOrderListPresenter(CancelOrderListView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new CannelOrderListModelImple() ;
    }

    public void getData(String id){
        model.getListData(view,id);
    }
    public void sh(String id,int status){
        model.shOrder(view,id,status);

    }



}

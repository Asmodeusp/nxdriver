package com.saimawzc.freight.presenter.order;

import android.content.Context;
import com.saimawzc.freight.modle.order.modelImple.CannelOrderModelImple;
import com.saimawzc.freight.modle.order.modle.bidd.CancelOrderModel;
import com.saimawzc.freight.view.order.error.CancelOrderView;
/**
 *申请撤销
 */

public class CancelOrderPresenter {

    private Context mContext;
    CancelOrderView view;
    CancelOrderModel model;
    public CancelOrderPresenter(CancelOrderView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new CannelOrderModelImple() ;
    }


    public void applySubmit(String id,String reason){
        model.submitError(view,id,reason);
    }

}

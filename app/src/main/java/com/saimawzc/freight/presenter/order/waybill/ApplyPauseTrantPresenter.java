package com.saimawzc.freight.presenter.order.waybill;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.modle.order.modelImple.ApplyPauseTrantModelImple;
import com.saimawzc.freight.modle.order.modelImple.StopTrantModelImple;
import com.saimawzc.freight.modle.order.modle.bidd.ApplyPauseTrantModel;
import com.saimawzc.freight.modle.order.modle.bidd.StopTrantModel;
import com.saimawzc.freight.view.BaseView;

/**
 * Created by Administrator on 2020/7/30.
 * 申请停运
 */

public class ApplyPauseTrantPresenter  {

    private Context mContext;
    BaseView view;
    ApplyPauseTrantModel model;
    public ApplyPauseTrantPresenter(BaseView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ApplyPauseTrantModelImple() ;
    }

    public void stopTrant(String id,String type,String reason){
        model.stopTrant(view,id,type,reason);
    }
    public void stopsjTrant(String id,String type,String reason){
        model.stopsjTrant(view,id,type,reason);
    }

}

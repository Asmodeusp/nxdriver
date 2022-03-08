package com.saimawzc.freight.presenter.order;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.modle.order.modelImple.StopTrantDelationModelImple;
import com.saimawzc.freight.modle.order.modelImple.StopTrantModelImple;
import com.saimawzc.freight.modle.order.modle.bidd.StopTrantDelationModel;
import com.saimawzc.freight.modle.order.modle.bidd.StopTrantModel;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.view.order.StopTrantDelationView;

/**
 * Created by Administrator on 2020/7/30.
 * 停运详情
 */

public class StopTrantDelationPresenter {

    private Context mContext;
    StopTrantDelationView view;
    StopTrantDelationModel model;
    public StopTrantDelationPresenter(StopTrantDelationView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new StopTrantDelationModelImple() ;
    }

    public void getDelation(String id){
        model.stopTrantDelation(view,id);
    }


}

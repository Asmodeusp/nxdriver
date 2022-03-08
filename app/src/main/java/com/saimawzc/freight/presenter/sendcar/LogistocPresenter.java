package com.saimawzc.freight.presenter.sendcar;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.sendcar.ArriverOrderDto;
import com.saimawzc.freight.modle.sendcar.imple.ArriverOrderModelImple;
import com.saimawzc.freight.modle.sendcar.imple.LogistoicModelImple;
import com.saimawzc.freight.modle.sendcar.model.ArriverOrderModel;
import com.saimawzc.freight.modle.sendcar.model.LogistocModel;
import com.saimawzc.freight.view.sendcar.ArriverOrderView;
import com.saimawzc.freight.view.sendcar.LogistoicView;

import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */

public class LogistocPresenter {

    private Context mContext;
    LogistoicView view;
    LogistocModel model;
    public LogistocPresenter(LogistoicView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new LogistoicModelImple() ;
    }

    public void getData(String id,String type){
        model.getData(view,id,type);
    }



}

package com.saimawzc.freight.presenter.sendcar;

import android.content.Context;


import com.saimawzc.freight.modle.sendcar.imple.WarnInfoModelImple;
import com.saimawzc.freight.modle.sendcar.model.WarnInfoModel;
import com.saimawzc.freight.view.sendcar.WarnInfoView;

/**
 * Created by Administrator on 2020/7/30.
 */

public class WarnInfoPresenter {

    private Context mContext;
    WarnInfoView view;
    WarnInfoModel model;
    public WarnInfoPresenter(WarnInfoView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new WarnInfoModelImple() ;
    }

    public void getData(String id,String type){
        model.getData(view,id,type);
    }



}

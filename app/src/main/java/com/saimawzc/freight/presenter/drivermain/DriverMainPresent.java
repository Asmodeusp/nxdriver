package com.saimawzc.freight.presenter.drivermain;

import android.content.Context;

import com.saimawzc.freight.modle.drivermain.DriverMainModel;
import com.saimawzc.freight.modle.drivermain.imple.DriverMainModelImple;
import com.saimawzc.freight.view.drivermain.DriverMainView;
/**
 * Created by Administrator on 2020/7/30.
 * 忘记密码
 */

public class DriverMainPresent {

    private Context mContext;
    DriverMainView view;
    DriverMainModel model;

    public DriverMainPresent(DriverMainView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new DriverMainModelImple();
    }

    public void getNeedFenceList(){
        model.getNeedFence(view);
    }
    public void getData(int page){
        model.getRobLsit(view,page);
    }

}

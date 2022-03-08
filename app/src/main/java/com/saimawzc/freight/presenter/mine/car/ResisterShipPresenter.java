package com.saimawzc.freight.presenter.mine.car;

import android.content.Context;

import com.saimawzc.freight.modle.mine.car.ship.ResisterShipModel;
import com.saimawzc.freight.modle.mine.car.ship.ResisterShipModelImple;
import com.saimawzc.freight.view.mine.car.ship.ResisterShipView;
import com.saimawzc.freight.weight.utils.WheelDialog;

/**
 * Created by Administrator on 2020/8/3.
 */

public class ResisterShipPresenter{
    private Context mContext;
    ResisterShipModel model;
    ResisterShipView view;
    private WheelDialog wheelDialog;

    public ResisterShipPresenter(ResisterShipView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ResisterShipModelImple();
    }

    public void showCamera( Context context,  int type){//type 0是身份证正面  1是身份证反面
        model.showCamera(context, type,view) ;
    }
    public void getCarType(){
        model.getShipType(view);
    }


    /**获取船舶详情***/
    public void getShipInfo(String id){
      model.getShipInfo(view,id);
    }


    public  void carriveRz(){
        model.identification(view);
    }



    /*被车辆是否被注册*
     *
     * **/
    public void isResister(String shipId){
      model.isregster(view,shipId);
    }

    /*被车辆是否被注册*
     *
     * **/
    public void ismodify(String shipId){
        model.ismodify(view,shipId);
    }

}

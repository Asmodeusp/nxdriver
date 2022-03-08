package com.saimawzc.freight.presenter.mine.car;

import android.content.Context;

import com.saimawzc.freight.modle.mine.car.ship.MyShipModel;
import com.saimawzc.freight.modle.mine.car.ship.MyShipModelImple;
import com.saimawzc.freight.view.mine.car.ship.MyShipView;

/**
 * Created by Administrator on 2020/8/8.
 * 我的车辆
 */
public class MyShipPresenter  {

    private Context mContext;
    MyShipModel model;
    MyShipView view;


    public MyShipPresenter(MyShipView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MyShipModelImple();

    }
    public void getcarList(int type,int page,String carNo){
        model.getCarList(view,type,page,carNo);
    }



}

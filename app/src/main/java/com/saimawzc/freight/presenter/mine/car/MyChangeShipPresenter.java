package com.saimawzc.freight.presenter.mine.car;

import android.content.Context;
import com.saimawzc.freight.modle.mine.car.ship.MyChangeShipModelImple;
import com.saimawzc.freight.modle.mine.car.ship.MyShipModel;
import com.saimawzc.freight.view.mine.car.ship.MyShipView;

/**
 * Created by Administrator on 2020/8/10.
 * 车辆变更
 */

public class MyChangeShipPresenter  {
    private Context mContext;
    MyShipModel model;
    MyShipView view;

    public MyChangeShipPresenter(MyShipView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MyChangeShipModelImple();

    }
    public void getcarList(int type,int page){
        model.getCarList(view,type,page,"");
    }



}

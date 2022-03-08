package com.saimawzc.freight.modle.mine.car.ship;

import com.saimawzc.freight.view.mine.car.ship.MyShipView;

/**
 * Created by Administrator on 2020/8/8.
 * 获取我的车辆 已经通过审核和未通过审核
 */

public interface MyShipModel {

    void getCarList(MyShipView view, int type, int page,String shipNo);
}

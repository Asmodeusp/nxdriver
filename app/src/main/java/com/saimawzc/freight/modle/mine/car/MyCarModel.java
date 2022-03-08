package com.saimawzc.freight.modle.mine.car;

import com.saimawzc.freight.view.mine.car.MyCarView;
import com.saimawzc.freight.view.mine.car.SearchCarView;
import com.saimawzc.freight.weight.utils.listen.car.SearchCarListener;

/**
 * Created by Administrator on 2020/8/8.
 * 获取我的车辆 已经通过审核和未通过审核
 */

public interface MyCarModel {

    void getCarList(MyCarView view, SearchCarListener listener, int type,int page,String carNo);
}

package com.saimawzc.freight.modle.mine.driver;

import com.saimawzc.freight.view.mine.car.MyCarView;
import com.saimawzc.freight.view.mine.driver.MyDriverView;
import com.saimawzc.freight.weight.utils.listen.car.SearchCarListener;
import com.saimawzc.freight.weight.utils.listen.driver.MyDriverListener;

/**
 * Created by Administrator on 2020/8/10.
 */

public interface MyDriverModel {
    void getDriverList(MyDriverView view, MyDriverListener listener, int type, int page,String userAccount);
}

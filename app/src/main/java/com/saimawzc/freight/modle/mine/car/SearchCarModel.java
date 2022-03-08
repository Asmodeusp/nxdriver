package com.saimawzc.freight.modle.mine.car;

import com.saimawzc.freight.view.mine.car.ResisterCarView;
import com.saimawzc.freight.view.mine.car.SearchCarView;
import com.saimawzc.freight.weight.utils.listen.car.SearchCarListener;

/**
 * Created by Administrator on 2020/8/8.
 */

public interface SearchCarModel {

    void getCarList(SearchCarView view);
}

package com.saimawzc.freight.modle.mine.driver;

import com.saimawzc.freight.view.mine.driver.SearchDriverView;
import com.saimawzc.freight.weight.utils.listen.driver.SearchDrivierListener;

/**
 * Created by Administrator on 2020/8/10.
 * 我的司机
 */

public interface SearchDriverModel {
    void getCarList(SearchDriverView view, SearchDrivierListener listener, String phone);
}

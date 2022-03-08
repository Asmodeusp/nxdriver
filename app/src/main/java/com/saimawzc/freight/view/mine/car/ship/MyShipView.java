package com.saimawzc.freight.view.mine.car.ship;

import com.saimawzc.freight.dto.my.car.ship.MyShipDto;
import com.saimawzc.freight.dto.my.car.ship.SearchShipDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2020/8/8.
 * 我的车辆tong
 */

public interface MyShipView extends BaseView{

    void compelete(MyShipDto shipDtos);

    void stopRefresh();
}

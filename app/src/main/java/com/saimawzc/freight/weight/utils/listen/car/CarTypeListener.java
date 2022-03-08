package com.saimawzc.freight.weight.utils.listen.car;

import com.saimawzc.freight.dto.my.CarTypeDo;
import com.saimawzc.freight.dto.my.car.CarIsRegsister;
import com.saimawzc.freight.dto.my.car.SearchCarDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 */

public interface CarTypeListener {

    void callbacktype(List<CarTypeDo> carTypeDos);
    void carinfoListen(SearchCarDto dto);

    void isRegsister(CarIsRegsister dto);
}

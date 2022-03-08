package com.saimawzc.freight.weight.utils.listen.car;

import com.saimawzc.freight.dto.my.CarBrandDto;
import com.saimawzc.freight.dto.my.CarTypeDo;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 */

public interface CarBrandListener {
    void callbackbrand(List<CarBrandDto> carTypeDos);
}

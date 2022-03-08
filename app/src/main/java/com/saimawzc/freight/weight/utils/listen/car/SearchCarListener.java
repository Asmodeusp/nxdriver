package com.saimawzc.freight.weight.utils.listen.car;

import com.saimawzc.freight.dto.my.car.MyCarDto;
import com.saimawzc.freight.dto.my.car.SearchCarDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 */

public interface SearchCarListener {
    void callbackbrand(MyCarDto searchCarDtos);

}

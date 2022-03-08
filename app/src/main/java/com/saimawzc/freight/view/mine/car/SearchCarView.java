package com.saimawzc.freight.view.mine.car;

import com.saimawzc.freight.dto.my.car.MyCarDto;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2020/8/8.
 * 搜索View
 */

public interface SearchCarView extends BaseView{

    String getCarNum();
    String getCompanyId();
    void compelete(List<SearchCarDto>carDtoList);

}

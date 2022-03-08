package com.saimawzc.freight.view.mine.car;

import android.support.v4.widget.SwipeRefreshLayout;

import com.saimawzc.freight.dto.my.car.MyCarDto;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2020/8/8.
 * 我的车辆tong
 */

public interface MyCarView extends BaseView{

    void compelete(MyCarDto carDtoList);
    void stopRefresh();
}

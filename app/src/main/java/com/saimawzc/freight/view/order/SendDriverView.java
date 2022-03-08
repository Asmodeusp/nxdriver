package com.saimawzc.freight.view.order;

import android.app.Activity;

import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.order.CarDriverDto;
import com.saimawzc.freight.dto.order.CarInfolDto;
import com.saimawzc.freight.dto.order.CarModelDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

/***
 * 获取司机
 * */
public interface SendDriverView extends BaseView {

    void getDriverList(List<CarDriverDto> dtos);

    void stopResh();
    void isLastPage(boolean isLastPage);

    BaseActivity getContect();

}

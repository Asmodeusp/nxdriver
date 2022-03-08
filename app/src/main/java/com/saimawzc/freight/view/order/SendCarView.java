package com.saimawzc.freight.view.order;

import com.saimawzc.freight.dto.order.CarInfolDto;
import com.saimawzc.freight.dto.order.CarModelDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

/***
 * 获取车型
 * */
public interface SendCarView extends BaseView {

    void getCarModelList(List<CarModelDto>dtos);

    void getCarInfolList(List<CarInfolDto.carInfoData>dtos);

    void isLastPage(boolean islastpage);

    void stopResh();
    void ishaveBeiDou(boolean stasue);

}

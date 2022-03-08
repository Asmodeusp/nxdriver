package com.saimawzc.freight.view.sendcar;

import com.saimawzc.freight.dto.order.SignWeightDto;
import com.saimawzc.freight.dto.sendcar.ArriverOrderDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface ArriverOrderView extends BaseView {
    void getData(List<ArriverOrderDto.materialsDto> datas);

    void ondealCamera(int type);
    void getSignWeiht(SignWeightDto dto);
    void oncomplete(String code);
    void isFence(int isFenceClock,String message);
}

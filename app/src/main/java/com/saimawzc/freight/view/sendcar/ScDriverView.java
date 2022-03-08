package com.saimawzc.freight.view.sendcar;

import com.saimawzc.freight.dto.sendcar.ScSearchDriverDto;
import com.saimawzc.freight.dto.sendcar.SendCarDelatiodto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface ScDriverView extends BaseView {

    void  getDriverList(List<ScSearchDriverDto> driverDtos);
    String getPhone();
    void stopRefresh();
}

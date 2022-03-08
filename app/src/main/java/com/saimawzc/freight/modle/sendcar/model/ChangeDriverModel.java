package com.saimawzc.freight.modle.sendcar.model;


import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.dto.my.driver.MyDriverDto;
import com.saimawzc.freight.dto.sendcar.ScSearchDriverDto;
import com.saimawzc.freight.view.BaseView;

public interface ChangeDriverModel {
    void changeDriver(BaseView view, String id, ScSearchDriverDto myDriverDto, String reason);


}

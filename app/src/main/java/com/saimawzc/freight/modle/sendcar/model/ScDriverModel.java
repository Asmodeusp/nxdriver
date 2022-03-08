package com.saimawzc.freight.modle.sendcar.model;


import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.view.sendcar.ScDriverView;

public interface ScDriverModel {

    void getDriverList(ScDriverView view, String id,String companyId);


}

package com.saimawzc.freight.modle.order.modle;

import com.saimawzc.freight.dto.order.CarDriverDto;
import com.saimawzc.freight.dto.order.CarInfolDto;
import com.saimawzc.freight.view.order.ManageOrderView;
import com.saimawzc.freight.view.order.SendDriverView;
import com.saimawzc.freight.weight.utils.listen.order.CarDriverListener;
import com.saimawzc.freight.weight.utils.listen.order.ManageOrderListener;

public interface CarDriverModel {
    void getCarDriverList(SendDriverView view, CarDriverListener listener,String phone,int trantType
    ,String carId,String companyId);

    void getDriverListByPage(SendDriverView view, CarDriverListener listener,String phone,int trantType
            ,String carId,int page,String companyId);

    void sendCar(SendDriverView view, CarDriverListener listener,
                 CarInfolDto.carInfoData carInfoData,
                 CarDriverDto driverDto,String type,String billId,String sendCarNum);
    void sendsjCar(SendDriverView view, CarDriverListener listener,
                 CarInfolDto.carInfoData carInfoData,
                 CarDriverDto driverDto,String type,String billId,String sendCarNum);
}

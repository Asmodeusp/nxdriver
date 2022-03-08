package com.saimawzc.freight.view.mine.car;

import com.saimawzc.freight.dto.my.CarBrandDto;
import com.saimawzc.freight.dto.my.CarTypeDo;
import com.saimawzc.freight.dto.my.car.CarIsRegsister;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.dto.my.car.TrafficDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2020/8/6.
 * 注册车辆
 */

public interface ResisterCarView extends BaseView{

    void OnDealCamera(int ype);
    String stringTransport();
    String  stringDrivinglicense();
    String  stringCarInfo();
    String getAllowQuali();//资质
    String getIsAllowAdd();//资质
    String carwith();
    String carLength();
    String carHeight();
    String getCarNum();
    String getCarColor();
    String getCarModel();//车型
    String getCarWeight();
    String getCarBrand();//品牌
    String getUser();
    String getTransportNum();//运输证号
    String getInvitEnter();

    String gettranCarNo();
    String gettranCarName();
    String getpictureTranNo();

    String isDangerous();
    String boardHeight();
    String licenseRegTime();
    String rib1();
    String rib2();
    String rib3();
    String rib4();
    String rib5();
    String location();

    TrafficDto tfaffic();



    void carTypeName(String cartypeName);
    void carTypeId(String carTypeId);
    void carBrandName(String carBrandName);
    void carBrandid(String carBrandId);

    String getCarTypeId();
    String getCarBrandId();
    void getCarInfo(SearchCarDto dto);
    void isResister(CarIsRegsister dto);

    void getTrafficDto(TrafficDto dto);
}

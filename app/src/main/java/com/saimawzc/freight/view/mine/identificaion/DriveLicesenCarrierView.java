package com.saimawzc.freight.view.mine.identificaion;

import com.saimawzc.freight.dto.my.identification.DriviceerIdentificationDto;
import com.saimawzc.freight.view.BaseView;

/**
 * Created by Administrator on 2020/8/3.
 * 司机认证
 */

public interface DriveLicesenCarrierView extends BaseView {

    void OnDealCamera(int ype);
    String sringImgPositive();
    String  sringImgOtherSide();
    String  sringDriverLincense();
    String getUser();
    String getArea();
    String getIdNum();
    String proviceName();
    String proviceId();
    String cityName();
    String cityId();
    String countryName();
    String countryId();
    String driverType();
    void getInditifacationInfo(DriviceerIdentificationDto dto);
    String driverName();
    String driverIdCard();
    String driverOneTime();


}

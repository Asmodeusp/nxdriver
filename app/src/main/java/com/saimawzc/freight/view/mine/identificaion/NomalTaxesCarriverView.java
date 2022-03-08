package com.saimawzc.freight.view.mine.identificaion;

import com.saimawzc.freight.dto.my.identification.CarrierIndenditicationDto;
import com.saimawzc.freight.view.BaseView;

/**
 * Created by Administrator on 2020/8/3.
 * 一般纳税人承运商认证
 *
 */

public interface NomalTaxesCarriverView extends BaseView{

    void OnDealCamera(int ype);
    String sringImgPositive();
    String  sringImgOtherSide();
    String  sringnomalTaxe();
    String  sringBussiselices();
    String  sringTransport();
    String  sringEntrust();
    String  sringFrIdcard();
    String getUser();
    String getIdNum();
    String getArea();
    String getCompanyName();
    String getIegalName();//法人姓名
    String getIegalCardNum();//法人身份证
    String getBusissNum();
    String getzzType();//营业执照类型
    String getRoadNum();//道路编号

    String proviceName();
    String proviceId();
    String cityName();
    String cityId();
    String countryName();
    String countryId();
    String invoiceMaxAmount();
    void identificationInfo(CarrierIndenditicationDto dto);

}

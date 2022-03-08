package com.saimawzc.freight.view.mine.identificaion;

import com.saimawzc.freight.dto.my.identification.CarrierIndenditicationDto;
import com.saimawzc.freight.view.BaseView;

/**
 * Created by Administrator on 2020/8/3.
 */

public interface PersonCarrierView extends BaseView {

    void OnDealCamera(int ype);
    String sringImgPositive();
    String  sringImgOtherSide();
    String getUser();
    String getArea();
    String getIdNum();

    String proviceName();
    String proviceId();
    String cityName();
    String cityId();
    String countryName();
    String countryId();
    String invoiceMaxAmount();

    void IdentificationInfo(CarrierIndenditicationDto dto);



}

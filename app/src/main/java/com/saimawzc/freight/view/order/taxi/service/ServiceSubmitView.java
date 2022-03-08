package com.saimawzc.freight.view.order.taxi.service;

import android.app.Activity;

import com.saimawzc.freight.dto.face.EditTaxaDto;
import com.saimawzc.freight.dto.taxi.service.CarInfo;
import com.saimawzc.freight.dto.taxi.service.ServiceInfo;
import com.saimawzc.freight.view.BaseView;

public interface ServiceSubmitView extends BaseView {
    void OnDealCamera(int ype);
    String carId();
    String clsyr();
    String cycc();
    String cycph();
    String cycsfgk();
    String cycsjsyr();
    String cycsyrlxfs();
    String cycx();
    String cycxszfjBos();
    String cyczpfjBos();
    String cypp();
    String cyzz();
    String czsmgkfjBos();
    String fwfsfzjhm();
    String fwfsfzjlx();
    String fwfuuid();
    String fwfxm();
    String jszfjBos();
    String jszjhm();
    String khrsfzjhm();
    String khrxm();
    String khyh();
    String ptzcsj();
    String qysmgkfjBos();
    String role();
    String roleId();
    String skzh();
    String smrzfjBos();
    String ssdq();
    String xszjhm();
    String yddh();
    void getData(EditTaxaDto dto);
    void successful(int type);

    void carBrandName(String carBrandName);
    void carBrandid(String carBrandId);

    void carTypeName(String carBrandName);
    void carTypeId(String carBrandId);

    void getSfInfo(ServiceInfo sfInfo);
    Activity getActivity();
    void getCarInfo(CarInfo carInfo);

}

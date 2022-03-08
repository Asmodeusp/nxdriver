package com.saimawzc.freight.view;

import com.saimawzc.freight.dto.FrameDto;
import com.saimawzc.freight.dto.WaybillEncloDto;
import com.saimawzc.freight.dto.my.carrier.MyCarrierDto;
import com.saimawzc.freight.dto.my.lessess.MyLessessDto;

import java.util.List;

public interface DriverMainView extends BaseView{
    void getMyCarrive(List<MyCarrierDto> carrierDtos);
    void getmylessee(List<MyLessessDto>lessessDtos);
    void getDialog(FrameDto dto);
    void getYdInfo(WaybillEncloDto dto);
}

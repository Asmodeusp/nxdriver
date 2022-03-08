package com.saimawzc.freight.view.sendcar;

import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.LcInfoDto;
import com.saimawzc.freight.dto.sendcar.WaitExecuteDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface WaitExecuteView extends BaseView {

    void isLastPage(boolean islastpage);
    void stopResh();
    void getSendCarList(List<WaitExecuteDto.WaitExecuteData>dtos);
    void getLcInfoDto(LcInfoDto lcInfoDto);
    void siloScanLock(LcInfoDto lcInfoDto);
    void siloScanUnlock(LcInfoDto lcInfoDto);
    void oncomplete(int position);
    BaseActivity getcontect();
}

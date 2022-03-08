package com.saimawzc.freight.view.sendcar;

import com.saimawzc.freight.dto.sendcar.CompleteExecuteDto;
import com.saimawzc.freight.dto.sendcar.WaitExecuteDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface CompleteExecuteView extends BaseView {

    void isLastPage(boolean islastpage);
    void stopResh();
    void getSendCarList(List<CompleteExecuteDto.ComeletaExecuteData> dtos);
}

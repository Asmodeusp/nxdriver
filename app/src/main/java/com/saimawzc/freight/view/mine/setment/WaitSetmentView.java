package com.saimawzc.freight.view.mine.setment;

import com.saimawzc.freight.dto.account.WaitComfirmAccountDto;
import com.saimawzc.freight.dto.account.WaitComfirmQueryPageDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

/***
 * 待结算大单
 * **/
public interface WaitSetmentView extends BaseView {
    void getData(WaitComfirmQueryPageDto dto);
    void stopResh();
}

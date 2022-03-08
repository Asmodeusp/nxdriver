package com.saimawzc.freight.view.mine.setment;

import com.saimawzc.freight.dto.account.WaitComfirmAccountDto;
import com.saimawzc.freight.dto.account.WaitDispatchDto;
import com.saimawzc.freight.dto.account.WaitDispatchQueryPageDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

/***
 * 待结算大单
 * **/
public interface WaitSetmentSmallOrderView extends BaseView {
    void getData(WaitDispatchQueryPageDto dto);
    void stopResh();
}

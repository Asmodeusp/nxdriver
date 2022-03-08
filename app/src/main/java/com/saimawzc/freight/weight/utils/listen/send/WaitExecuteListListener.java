package com.saimawzc.freight.weight.utils.listen.send;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.LcInfoDto;
import com.saimawzc.freight.dto.order.bill.WayBillDto;
import com.saimawzc.freight.dto.sendcar.WaitExecuteDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 *
 */

public interface WaitExecuteListListener extends BaseListener {


    void getDatas(List<WaitExecuteDto.WaitExecuteData> dtos);
    void getLcInfoDto(LcInfoDto dtos);


}

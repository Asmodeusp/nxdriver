package com.saimawzc.freight.weight.utils.listen.send;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.sendcar.CompleteExecuteDto;
import com.saimawzc.freight.dto.sendcar.WaitExecuteDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 *
 */

public interface CompleteExecuteListListener extends BaseListener {


    void getDatas(List<CompleteExecuteDto.ComeletaExecuteData> dtos);

}

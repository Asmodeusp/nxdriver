package com.saimawzc.freight.weight.utils.listen.order.bidd;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.order.CarDriverDto;
import com.saimawzc.freight.dto.order.bidd.JoinBiddDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 *
 */

public interface JoinBiddListener extends BaseListener {


    void getBiddDelation(JoinBiddDto dtos);

}

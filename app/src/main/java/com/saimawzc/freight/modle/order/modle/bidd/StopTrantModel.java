package com.saimawzc.freight.modle.order.modle.bidd;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.order.AddWayBillDto;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.view.order.AddwaybillView;

import java.util.List;
/**
 * 关闭派车单
 * **/
public interface StopTrantModel {

    void stopTrant(BaseView view, BaseListener listener, String id,String reason);
}

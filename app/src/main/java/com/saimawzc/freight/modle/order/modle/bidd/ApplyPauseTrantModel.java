package com.saimawzc.freight.modle.order.modle.bidd;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.view.BaseView;

/**
 * 申请停运
 * **/
public interface ApplyPauseTrantModel {

    void stopTrant(BaseView view, String id, String type,String reason);
    void stopsjTrant(BaseView view, String id, String type,String reason);
}

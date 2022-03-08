package com.saimawzc.freight.modle.sendcar.model;


import com.saimawzc.freight.view.sendcar.WaitExecuteView;
import com.saimawzc.freight.weight.utils.listen.send.WaitExecuteListListener;

public interface WaitExecuteModel {
    void getSendLsit(WaitExecuteView view, WaitExecuteListListener listener,
                     int page,String type,String value);

    void startTask(WaitExecuteView view,String id,int position);

    void getLcInfoDto(WaitExecuteView view,WaitExecuteListListener listener,String lcbh,
                      String dispatchCarId,String czbm,String companyId);
    void siloScanUnlock(WaitExecuteView view,WaitExecuteListListener listener,String dispatchCarId, String dispatchCarNo, String lcbh);
    void siloScanLock(WaitExecuteView view,WaitExecuteListListener listener,String dispatchCarId, String dispatchCarNo, String lcbh);
}

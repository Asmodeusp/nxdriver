package com.saimawzc.freight.modle.sendcar.model;


import com.saimawzc.freight.view.sendcar.CompleteExecuteView;
import com.saimawzc.freight.weight.utils.listen.send.CompleteExecuteListListener;

public interface CompleteExecuteModel {
    void getSendLsit(CompleteExecuteView view, CompleteExecuteListListener listener,
                     int page, String type, String value,String startTime,String endTime,int status);


}

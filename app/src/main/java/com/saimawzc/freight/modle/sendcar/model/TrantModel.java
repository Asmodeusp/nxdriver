package com.saimawzc.freight.modle.sendcar.model;


import android.content.Context;

import com.baidu.location.BDLocation;
import com.saimawzc.freight.view.sendcar.DriverTransportView;
import com.saimawzc.freight.view.sendcar.ScDriverView;

public interface TrantModel {

    void getTrant(DriverTransportView view, String id,int type);
    void daka(DriverTransportView view, String id, String type,
              BDLocation location, String pic);

     void roulete(DriverTransportView view, String id,int type);

    void getWayBillRolete(DriverTransportView view, String id,String type);

    void isFenceClock(DriverTransportView view,String id,String location,String operType);
    void showCamera(Context context,DriverTransportView view);

}

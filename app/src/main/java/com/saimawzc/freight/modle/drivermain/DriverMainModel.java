package com.saimawzc.freight.modle.drivermain;
import com.saimawzc.freight.view.drivermain.DriverMainView;
public interface DriverMainModel {
    void getNeedFence(DriverMainView view);
    void getRobLsit(DriverMainView view,
                    int page);
}

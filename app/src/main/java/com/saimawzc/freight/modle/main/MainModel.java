package com.saimawzc.freight.modle.main;

import com.saimawzc.freight.view.DriverMainView;

import java.util.List;

public interface MainModel {
    void getCarrierList(DriverMainView view);
    void getLessessList(DriverMainView view);
    void getDlilog(DriverMainView view);

    void getYdInfo(DriverMainView view);

    void uploadGwWl(DriverMainView view, String waybillId, String highEnclosureId,
                    String warnType, String location, List<String> alreadyList);
}

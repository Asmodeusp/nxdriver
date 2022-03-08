package com.saimawzc.freight.modle.travel;

import com.saimawzc.freight.view.travel.TravelView;

public interface TravelModel {

    void getBeiDouTravel(TravelView view,String id);
    void getPreSupTravel(TravelView view,String id);
}

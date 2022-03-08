package com.saimawzc.freight.view.travel;

import com.saimawzc.freight.dto.travel.BeidouTravelDto;
import com.saimawzc.freight.dto.travel.PresupTravelDto;
import com.saimawzc.freight.view.BaseView;

public interface TravelView extends BaseView {

    void getBeiDouTravel(BeidouTravelDto dto);
    void getPreSupTravel(PresupTravelDto dto);

}

package com.saimawzc.freight.view.order.taxi;

import android.app.Activity;

import com.saimawzc.freight.dto.taxi.TjSWDto;
import com.saimawzc.freight.view.BaseView;
import java.util.List;

public interface PublisherView extends BaseView {


    void getTaxiDto(TjSWDto dto);
    Activity getActivity();



}

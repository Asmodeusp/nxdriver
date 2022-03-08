package com.saimawzc.freight.modle.order.modle.taxi;


import com.saimawzc.freight.dto.taxi.TjSubmitDto;
import com.saimawzc.freight.view.order.taxi.PublisherView;

public interface PublisherModel {

    void getTaxiPush(PublisherView view, String roleId);

    void submit(PublisherView view, TjSubmitDto dto);
}

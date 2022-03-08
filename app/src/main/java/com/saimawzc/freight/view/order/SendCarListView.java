package com.saimawzc.freight.view.order;

import com.saimawzc.freight.dto.order.SendCarDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface SendCarListView extends BaseView {
    void getSendCarList(List<SendCarDto.SendCarData> dtos);
    void stopResh();
    void isLastPage(boolean isLastPage);
}

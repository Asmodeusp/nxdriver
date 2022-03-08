package com.saimawzc.freight.modle.order.modle;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.order.AddWayBillDto;
import com.saimawzc.freight.view.order.AddwaybillView;
import com.saimawzc.freight.view.order.SendCarListView;
import com.saimawzc.freight.weight.utils.listen.order.SendCarListListener;

import java.util.List;

public interface SendCarLsitModel {

    void getSendCarLsit(SendCarListView view, SendCarListListener listener,int page,
                        String type,String searchType,String searchValue);
}

package com.saimawzc.freight.modle.order.modle;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.order.AddWayBillDto;
import com.saimawzc.freight.view.order.AddwaybillView;


import java.util.List;

public interface AddWayBillModel {

    void addWayBill(AddwaybillView view, BaseListener listener, String id, List<AddWayBillDto>dtos);
    void addsjWayBill(AddwaybillView view, BaseListener listener, String id, List<AddWayBillDto>dtos);

}

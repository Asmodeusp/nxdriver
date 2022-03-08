package com.saimawzc.freight.weight.utils.listen.order;



import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.order.OrderInventoryDto;

import java.util.List;

public interface WayBillInventoryListener extends BaseListener {

    void back(List<OrderInventoryDto.qdData> dtos);
}

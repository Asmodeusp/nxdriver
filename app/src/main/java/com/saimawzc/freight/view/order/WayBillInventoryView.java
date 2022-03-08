package com.saimawzc.freight.view.order;



import com.saimawzc.freight.dto.order.OrderInventoryDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

/***
 * 预运单清单
 * */
public interface WayBillInventoryView extends BaseView {
    void getInventoryList(List<OrderInventoryDto.qdData> dtos);
}

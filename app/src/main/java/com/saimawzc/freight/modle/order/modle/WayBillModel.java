package com.saimawzc.freight.modle.order.modle;
import com.saimawzc.freight.view.order.PlanOrderView;
import com.saimawzc.freight.view.order.WayBillView;
import com.saimawzc.freight.weight.utils.listen.order.PlanOrderListener;
import com.saimawzc.freight.weight.utils.listen.order.WayBillListener;

public interface WayBillModel {

    void getWayBill(WayBillView view, WayBillListener listener,
                    int page,String seatchType,String searchValue);

    void delete(WayBillView view, WayBillListener listener,String id);


    void getsjWayBill(WayBillView view, WayBillListener listener,
                    int page,String seatchType,String searchValue);

    void sjdelete(WayBillView view, WayBillListener listener,String id);
}

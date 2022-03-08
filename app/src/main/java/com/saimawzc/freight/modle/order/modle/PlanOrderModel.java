package com.saimawzc.freight.modle.order.modle;
import com.saimawzc.freight.view.order.PlanOrderView;
import com.saimawzc.freight.weight.utils.listen.order.PlanOrderListener;

public interface PlanOrderModel {

    void getCarList(PlanOrderView view, PlanOrderListener listener,
                    int page,String searchType,String searchValue,int waybillstatus);

    void getsjCarList(PlanOrderView view, PlanOrderListener listener,
                    int page,String searchType,String searchValue);

    void reshData(PlanOrderView view,int position,String id);
}

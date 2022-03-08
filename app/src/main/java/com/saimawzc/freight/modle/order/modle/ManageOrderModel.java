package com.saimawzc.freight.modle.order.modle;

import com.saimawzc.freight.view.order.ManageOrderView;
import com.saimawzc.freight.weight.utils.listen.order.ManageOrderListener;

public interface ManageOrderModel {
    void getCarList(ManageOrderView view, ManageOrderListener listener,
                    int page,String searchType,String searchValue);

    void delete(ManageOrderView view, ManageOrderListener listener,
                    String id);

    void getsjCarList(ManageOrderView view, ManageOrderListener listener,
                    int page,String searchType,String searchValue);

    void sjdelete(ManageOrderView view, ManageOrderListener listener,
                String id);
}

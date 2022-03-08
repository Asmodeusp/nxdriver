package com.saimawzc.freight.modle.mine.mysetment;


import com.saimawzc.freight.view.mine.setment.ConsignCompanyView;
import com.saimawzc.freight.weight.utils.listen.order.ConsignCompanyListener;

public interface ConsignSignCompanyModel {

    void getCompanyList(ConsignCompanyView view, ConsignCompanyListener listener);
}

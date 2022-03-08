package com.saimawzc.freight.modle.mine.mysetment;


import com.saimawzc.freight.view.mine.setment.WaitSetmentSmallOrderView;
import com.saimawzc.freight.view.mine.setment.WaitSetmentView;

import java.util.List;

public interface WaitSetmentSmallOrderModel {

    void getList(int page,String id,WaitSetmentSmallOrderView view);

    void addsetment(List<String>idList,WaitSetmentSmallOrderView view);
}

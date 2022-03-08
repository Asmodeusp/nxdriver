package com.saimawzc.freight.modle.mine.lessess;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.view.mine.carrier.MyCarrierView;
import com.saimawzc.freight.view.mine.lessess.MyLessessView;
import com.saimawzc.freight.weight.utils.listen.carrier.MyCarrierListener;
import com.saimawzc.freight.weight.utils.listen.lessess.MyLessessListener;

/**
 * Created by Administrator on 2020/8/10.
 * 我的承租人
 */

public interface MyLessessModel {
    void getMyLessess(MyLessessView view, MyLessessListener listener, int type, int page,String carNo);
    void agreenApply(String id, String status, MyLessessView view,BaseListener listener,int type);
}

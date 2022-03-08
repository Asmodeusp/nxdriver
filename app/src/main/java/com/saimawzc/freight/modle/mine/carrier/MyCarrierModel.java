package com.saimawzc.freight.modle.mine.carrier;

import com.saimawzc.freight.view.mine.carrier.MyCarrierView;
import com.saimawzc.freight.weight.utils.listen.carrier.MyCarrierListener;

/**
 * Created by Administrator on 2020/8/10.
 */

public interface MyCarrierModel {
    void getCarrier(MyCarrierView view, MyCarrierListener listener, int type, int page,String carNo);
}

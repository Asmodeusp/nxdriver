package com.saimawzc.freight.view.mine.carrier;

import com.saimawzc.freight.dto.my.carrier.CarrierPageDto;
import com.saimawzc.freight.dto.my.carrier.MyCarrierDto;
import com.saimawzc.freight.dto.my.driver.MyDriverDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2020/8/10.
 */

public interface MyCarrierView extends BaseView{
    void getMyCarrierList(CarrierPageDto carrierDtos);
    void stopRefresh();

}

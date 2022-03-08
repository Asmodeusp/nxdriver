package com.saimawzc.freight.modle;

import com.saimawzc.freight.weight.utils.api.auto.AuthApi;
import com.saimawzc.freight.weight.utils.api.bms.BmsApi;
import com.saimawzc.freight.weight.utils.api.mine.MineApi;
import com.saimawzc.freight.weight.utils.api.order.OrderApi;
import com.saimawzc.freight.weight.utils.api.tms.TmsApi;
import com.saimawzc.freight.weight.utils.http.Http;

/**
 * Created by Administrator on 2020/7/30.
 */

public class BasEModeImple {

    public AuthApi authApi= Http.http.createApi(AuthApi.class);
    public MineApi mineApi= Http.http.createApi(MineApi.class);
    public OrderApi orderApi= Http.http.createApi(OrderApi.class);
    public TmsApi tmsApi= Http.http.createApi(TmsApi.class);
    public BmsApi bmsApi= Http.http.createApi(BmsApi.class);


}

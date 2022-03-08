package com.saimawzc.freight.view.order.taxi;


import com.saimawzc.freight.dto.login.TaxiAreaDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface TaxiAdressView extends BaseView {


    void getadressList(List<TaxiAreaDto> dtos);


}

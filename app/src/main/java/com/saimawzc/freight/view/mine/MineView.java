package com.saimawzc.freight.view.mine;

import com.saimawzc.freight.dto.my.PersonCenterDto;
import com.saimawzc.freight.dto.my.carrier.MyCarrierDto;
import com.saimawzc.freight.dto.my.lessess.MyLessessDto;
import com.saimawzc.freight.dto.wallet.SonAccountDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2020/8/1.
 */

public interface MineView extends BaseView {

    void getPersonDto(PersonCenterDto personCenterDto);
    void getMyCarrive(List<MyCarrierDto>carrierDtos);
    void getmylessee(List<MyLessessDto>lessessDtos);
    void getsonAccount(SonAccountDto sonAccountDto,String code);


}

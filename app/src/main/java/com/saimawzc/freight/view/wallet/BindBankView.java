package com.saimawzc.freight.view.wallet;

import com.saimawzc.freight.dto.wallet.MsBankDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface BindBankView extends BaseView {
    void getBigBankList(List<MsBankDto>dtos);
    void getCarBin(MsBankDto carBinDto);



}

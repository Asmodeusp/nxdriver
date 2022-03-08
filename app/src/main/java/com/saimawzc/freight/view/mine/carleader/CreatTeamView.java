package com.saimawzc.freight.view.mine.carleader;

import com.saimawzc.freight.dto.wallet.MsBankDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface CreatTeamView extends BaseView {
    void getBigBankList(List<MsBankDto> dtos);
    void getCarBin(MsBankDto carBinDto);



}

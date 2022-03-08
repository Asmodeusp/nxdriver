package com.saimawzc.freight.modle.mine.carleader;

import com.saimawzc.freight.dto.wallet.BindBankDto;
import com.saimawzc.freight.view.mine.carleader.CreatTeamView;
public interface CreatTeamModel {

    void bind(CreatTeamView view, BindBankDto dto);

    void getBigBank(CreatTeamView view, String str);

    void cardBin(CreatTeamView view, String cardNo);

}

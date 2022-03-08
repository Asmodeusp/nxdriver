package com.saimawzc.freight.modle.wallet;

import com.saimawzc.freight.dto.wallet.BindBankDto;
import com.saimawzc.freight.dto.wallet.MsBankDto;
import com.saimawzc.freight.view.wallet.BindBankView;

public interface BindBankModel {

    void bind(BindBankView view, BindBankDto dto);

    void getBigBank(BindBankView view, String str);

    void cardBin(BindBankView view, String cardNo);

}

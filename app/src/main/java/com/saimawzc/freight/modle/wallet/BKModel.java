package com.saimawzc.freight.modle.wallet;

import com.saimawzc.freight.dto.wallet.BindBankDto;
import com.saimawzc.freight.view.wallet.BKView;

public interface BKModel {
    void bind(BKView arg1, BindBankDto arg2);

    void cardBin(BKView arg1, String arg2);

    void getBigBank(BKView arg1, String arg2);
}

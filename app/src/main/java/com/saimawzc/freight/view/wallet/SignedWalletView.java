package com.saimawzc.freight.view.wallet;

import com.saimawzc.freight.dto.wallet.SonAccountDto;
import com.saimawzc.freight.view.BaseView;

public interface SignedWalletView extends BaseView {
    void getSonAccoucnt(SonAccountDto dto);
}

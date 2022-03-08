package com.saimawzc.freight.modle.wallet;

import com.saimawzc.freight.view.wallet.SignedWalletView;

public interface SonAccountModel {

    void getSonAccount(SignedWalletView view);

    void setDefaultCrd(SignedWalletView arg1, String arg2);

}

package com.saimawzc.freight.modle.wallet;


import com.saimawzc.freight.view.wallet.MsBankView;

public interface MsBankModel {

    void getBankList(MsBankView view, String bankName);
    void getBigBankList(MsBankView view, String queryBankName);

}

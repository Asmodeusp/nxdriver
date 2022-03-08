package com.saimawzc.freight.presenter.wallet;

import android.content.Context;
import com.saimawzc.freight.modle.wallet.MsBankModel;
import com.saimawzc.freight.modle.wallet.imple.MsBankModelImple;
import com.saimawzc.freight.view.wallet.MsBankView;

public class MsBankPresenter {
    private Context mContext;
    MsBankView view;
    MsBankModel model;

    public MsBankPresenter(MsBankView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MsBankModelImple() ;
    }
    public void getBinkList(String bankNmae){
        model.getBankList(view,bankNmae);
    }
    /**
     * 获取大额行号
     * **/
    public void getBigBank(String queryBankName){
        model.getBigBankList(view,queryBankName);
    }
}

package com.saimawzc.freight.presenter.wallet;

import android.content.Context;

import com.saimawzc.freight.dto.wallet.BindBankDto;
import com.saimawzc.freight.modle.wallet.BKModel;
import com.saimawzc.freight.modle.wallet.imple.BkModelImple;
import com.saimawzc.freight.view.wallet.BKView;

public class BKPresenter {
    private Context mContext;
    BKModel model;
    BKView view;

    public BKPresenter(BKView arg1, Context arg2) {
        super();
        this.view = arg1;
        this.mContext = arg2;
        this.model = new BkModelImple();
    }

    public void bind(BindBankDto arg3) {
        this.model.bind(this.view, arg3);
    }

    public void cardBin(String arg3) {
        this.model.cardBin(this.view, arg3);
    }
}

package com.saimawzc.freight.presenter.wallet;

import android.content.Context;

import com.saimawzc.freight.dto.wallet.BindBankDto;
import com.saimawzc.freight.modle.wallet.BindBankModel;
import com.saimawzc.freight.modle.wallet.imple.BindBankModelImple;
import com.saimawzc.freight.view.wallet.BindBankView;
public class BindBankPresenter {
    private Context mContext;
    BindBankView view;
    BindBankModel model;

    public BindBankPresenter(BindBankView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new BindBankModelImple() ;
    }
    public void bind(BindBankDto dto){
        model.bind(view,dto);
    }

    public void cardBin(String cardNo){
        model.cardBin(view,cardNo);
    }

}

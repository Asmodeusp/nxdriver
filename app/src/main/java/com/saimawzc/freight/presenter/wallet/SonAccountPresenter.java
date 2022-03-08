package com.saimawzc.freight.presenter.wallet;
import android.content.Context;
import com.saimawzc.freight.modle.wallet.SonAccountModel;
import com.saimawzc.freight.modle.wallet.imple.SonAccountModelImple;
import com.saimawzc.freight.view.wallet.SignedWalletView;

public class SonAccountPresenter {
    private Context mContext;
    SignedWalletView view;
    SonAccountModel model;

    public SonAccountPresenter(SignedWalletView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new SonAccountModelImple() ;
    }
    public void getSonAccount(){
        model.getSonAccount(view);
    }
    public void setDefaultcard(String arg3) {
        model.setDefaultCrd(this.view, arg3);
    }


}

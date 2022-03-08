package com.saimawzc.freight.presenter.wallet;

import android.content.Context;
import com.saimawzc.freight.modle.wallet.WithDrawModel;
import com.saimawzc.freight.modle.wallet.imple.WithDrawModelImple;
import com.saimawzc.freight.view.wallet.WithDrawView;

public class WithDrawPresenter {
    private Context mContext;
    WithDrawView view;
    WithDrawModel model;

    public WithDrawPresenter(WithDrawView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new WithDrawModelImple() ;
    }
    public void getCode(){
        model.getCode(view);
    }

    public void withdraw(String money,String code){
        model.withDraw(view,money,code);
    }

}

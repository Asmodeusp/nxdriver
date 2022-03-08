package com.saimawzc.freight.presenter.order.taxi;

import android.content.Context;
import com.saimawzc.freight.modle.order.modelImple.taxi.TaxiAdressModelImple;
import com.saimawzc.freight.modle.order.modle.taxi.TaxiAdressModel;
import com.saimawzc.freight.view.order.taxi.TaxiAdressView;

/**
 *申请撤销
 */

public class TaxiChooseAdressPresenter {

    private Context mContext;
    TaxiAdressView view;
    TaxiAdressModel model;

    public TaxiChooseAdressPresenter(TaxiAdressView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new TaxiAdressModelImple() ;
    }
    public void getAdress(String pid){
        model.getAdressList(view,pid);
    }
}

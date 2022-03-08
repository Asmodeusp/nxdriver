package com.saimawzc.freight.presenter.wallet;

import android.content.Context;

import com.saimawzc.freight.dto.wallet.TradeChooseDto;
import com.saimawzc.freight.modle.wallet.TradeDealtionModel;
import com.saimawzc.freight.modle.wallet.imple.TradeDelationModelImple;

import com.saimawzc.freight.view.wallet.TradeDelationView;

public class TradeDelationPresenter {
    private Context mContext;
    TradeDelationView view;
    TradeDealtionModel model;

    public TradeDelationPresenter(TradeDelationView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new TradeDelationModelImple() ;
    }

    public void getList(int page ,TradeChooseDto dto){
        model.getTradeList(view,page,dto);
    }



}

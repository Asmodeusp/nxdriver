package com.saimawzc.freight.presenter.travel;

import android.content.Context;

import com.saimawzc.freight.modle.travel.TravelModel;
import com.saimawzc.freight.modle.travel.imple.TravelModelImple;
import com.saimawzc.freight.view.travel.TravelView;

public class TravelPresenter {
    private Context mContext;
    TravelView view;
    TravelModel model;

    public TravelPresenter(TravelView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new TravelModelImple() ;
    }
    public void getBeiDou(String id){
        model.getBeiDouTravel(view,id);
    }
    public void getSuptravel(String id){
        model.getPreSupTravel(view,id);
    }
}

package com.saimawzc.freight.presenter.mine.car;

import android.content.Context;

import com.saimawzc.freight.modle.mine.car.ship.SearchShipModel;
import com.saimawzc.freight.modle.mine.car.ship.SearchShipModelImple;
import com.saimawzc.freight.view.mine.car.ship.SearchShipView;

/**
 * Created by Administrator on 2020/8/8.
 */

public class SearchShipPresenter {


    private Context mContext;
    SearchShipModel model;
    SearchShipView view;


    public SearchShipPresenter(SearchShipView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new SearchShipModelImple();

    }
    public void getcarList(String key){
        model.getShipList(view,key);
    }




}

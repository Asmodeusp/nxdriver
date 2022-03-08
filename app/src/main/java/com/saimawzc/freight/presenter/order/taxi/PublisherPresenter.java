package com.saimawzc.freight.presenter.order.taxi;

import android.content.Context;

import com.saimawzc.freight.dto.taxi.TjSubmitDto;
import com.saimawzc.freight.modle.order.modelImple.taxi.PublisherModelImple;
import com.saimawzc.freight.modle.order.modelImple.taxi.TaxiAdressModelImple;
import com.saimawzc.freight.modle.order.modle.taxi.PublisherModel;
import com.saimawzc.freight.modle.order.modle.taxi.TaxiAdressModel;
import com.saimawzc.freight.view.order.taxi.PublisherView;
import com.saimawzc.freight.view.order.taxi.TaxiAdressView;

/**
 *
 */

public class PublisherPresenter {

    private Context mContext;
    PublisherView view;
    PublisherModel model;

    public PublisherPresenter(PublisherView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new PublisherModelImple() ;
    }
    public void getData(String pid){
        model.getTaxiPush(view,pid);
    }
    public void submit(TjSubmitDto submitDto){
        model.submit(view,submitDto);
    }
}

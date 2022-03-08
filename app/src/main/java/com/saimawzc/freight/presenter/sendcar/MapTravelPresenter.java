package com.saimawzc.freight.presenter.sendcar;

import android.content.Context;
import com.saimawzc.freight.modle.sendcar.imple.MapTravelModelImple;
import com.saimawzc.freight.modle.sendcar.model.MapTravelModel;
import com.saimawzc.freight.view.sendcar.MapTravelView;

/**
 * Created by Administrator on 2020/7/30.
 */

public class MapTravelPresenter {

    private Context mContext;
    MapTravelView view;
    MapTravelModel model;

    public MapTravelPresenter(MapTravelView view, Context context) {
        this.mContext=context;
        this.view=view;
        model=new MapTravelModelImple() ;
    }
    public void getData(String id){
        model.roulete(view,id);
    }
}

package com.saimawzc.freight.presenter.sendcar;

import android.content.Context;

import com.saimawzc.freight.dto.my.car.MyCarDto;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.modle.mine.car.SearchCarModel;
import com.saimawzc.freight.modle.sendcar.imple.ChangeCarModelImple;
import com.saimawzc.freight.modle.sendcar.imple.ScSearchCarModelImple;
import com.saimawzc.freight.modle.sendcar.model.ChangeCarModel;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.view.mine.car.SearchCarView;
import com.saimawzc.freight.weight.utils.listen.car.SearchCarListener;

import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */

public class ScSearchCarPresenter  {

    private Context mContext;
    SearchCarView view;
    SearchCarModel model;
    public ScSearchCarPresenter(SearchCarView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ScSearchCarModelImple() ;
    }

    public void getData(){
        model.getCarList(view);
    }



}

package com.saimawzc.freight.presenter.sendcar;

import android.content.Context;

import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.dto.sendcar.ScSearchDriverDto;
import com.saimawzc.freight.modle.sendcar.imple.ChangeCarModelImple;
import com.saimawzc.freight.modle.sendcar.imple.ChangeDriverModelImple;
import com.saimawzc.freight.modle.sendcar.model.ChangeCarModel;
import com.saimawzc.freight.modle.sendcar.model.ChangeDriverModel;
import com.saimawzc.freight.view.BaseView;

/**
 * Created by Administrator on 2020/7/30.
 */

public class ChangeDriverPresenter {

    private Context mContext;
    BaseView view;
    ChangeDriverModel model;
    public ChangeDriverPresenter(BaseView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ChangeDriverModelImple() ;
    }

    public void getData(String id , ScSearchDriverDto searchCarDto, String reason){
        model.changeDriver(view,id,searchCarDto,reason);
    }





}

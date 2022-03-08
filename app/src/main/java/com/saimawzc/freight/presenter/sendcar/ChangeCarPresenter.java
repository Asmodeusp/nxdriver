package com.saimawzc.freight.presenter.sendcar;

import android.content.Context;

import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.dto.sendcar.CompleteExecuteDto;
import com.saimawzc.freight.modle.sendcar.imple.ChangeCarModelImple;
import com.saimawzc.freight.modle.sendcar.imple.CompeleteExecuteModelImple;
import com.saimawzc.freight.modle.sendcar.model.ChangeCarModel;
import com.saimawzc.freight.modle.sendcar.model.CompleteExecuteModel;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.view.sendcar.CompleteExecuteView;
import com.saimawzc.freight.weight.utils.listen.send.CompleteExecuteListListener;

import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */

public class ChangeCarPresenter  {

    private Context mContext;
    BaseView view;
    ChangeCarModel model;
    public ChangeCarPresenter(BaseView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ChangeCarModelImple() ;
    }

    public void getData(String id , SearchCarDto searchCarDto,String reason){
        model.changeCar(view,id,searchCarDto,reason);
    }





}

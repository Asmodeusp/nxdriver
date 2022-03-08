package com.saimawzc.freight.presenter.mine.car;

import android.content.Context;

import com.saimawzc.freight.dto.my.car.MyCarDto;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.modle.mine.car.MyCarModel;
import com.saimawzc.freight.modle.mine.car.MyCarModelImple;
import com.saimawzc.freight.modle.mine.car.MyChangeCarModel;
import com.saimawzc.freight.modle.mine.car.MyChangeCarModelImple;
import com.saimawzc.freight.view.mine.car.MyCarView;
import com.saimawzc.freight.weight.utils.listen.car.SearchCarListener;

import java.util.List;

/**
 * Created by Administrator on 2020/8/10.
 * 车辆变更
 */

public class MyChangeCarPresenter implements SearchCarListener {
    private Context mContext;
    MyChangeCarModel model;
    MyCarView view;

    public MyChangeCarPresenter(MyCarView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MyChangeCarModelImple();

    }
    public void getcarList(int type,int page){
        model.getCarList(view,this,type,page);
    }


    @Override
    public void callbackbrand(MyCarDto searchCarDtos) {
        view.compelete(searchCarDtos);

    }


}

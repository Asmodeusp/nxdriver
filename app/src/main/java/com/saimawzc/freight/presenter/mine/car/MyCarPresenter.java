package com.saimawzc.freight.presenter.mine.car;

import android.content.Context;

import com.saimawzc.freight.dto.my.car.MyCarDto;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.modle.mine.car.MyCarModel;
import com.saimawzc.freight.modle.mine.car.MyCarModelImple;
import com.saimawzc.freight.view.mine.car.MyCarView;
import com.saimawzc.freight.weight.utils.listen.car.SearchCarListener;

import java.util.List;

/**
 * Created by Administrator on 2020/8/8.
 * 我的车辆
 */
public class MyCarPresenter implements SearchCarListener {
    
    private Context mContext;
    MyCarModel model;
    MyCarView view;


    public MyCarPresenter(MyCarView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MyCarModelImple();

    }
    public void getcarList(int type,int page,String carNo){
        model.getCarList(view,this,type,page,carNo);
    }


    @Override
    public void callbackbrand(MyCarDto searchCarDtos) {
        view.compelete(searchCarDtos);

    }


}

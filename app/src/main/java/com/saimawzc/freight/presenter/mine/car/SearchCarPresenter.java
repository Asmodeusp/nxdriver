package com.saimawzc.freight.presenter.mine.car;

import android.content.Context;

import com.saimawzc.freight.dto.my.car.MyCarDto;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.modle.mine.car.SearchCarModel;
import com.saimawzc.freight.modle.mine.car.SearchCarModelImple;
import com.saimawzc.freight.view.mine.car.SearchCarView;
import com.saimawzc.freight.weight.utils.listen.car.SearchCarListener;

import java.util.List;

/**
 * Created by Administrator on 2020/8/8.
 */

public class SearchCarPresenter{


    private Context mContext;
    SearchCarModel model;
    SearchCarView view;


    public SearchCarPresenter(SearchCarView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new SearchCarModelImple();

    }
    public void getcarList(){
        model.getCarList(view);
    }





}

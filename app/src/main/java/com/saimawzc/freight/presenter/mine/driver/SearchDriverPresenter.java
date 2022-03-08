package com.saimawzc.freight.presenter.mine.driver;

import android.content.Context;

import com.saimawzc.freight.dto.my.driver.SearchDrivierDto;
import com.saimawzc.freight.modle.mine.driver.SearchDriverModel;
import com.saimawzc.freight.modle.mine.driver.SearchDriverModelImple;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.view.mine.driver.SearchDriverView;
import com.saimawzc.freight.weight.utils.listen.driver.SearchDrivierListener;

/**
 * Created by Administrator on 2020/8/10.
 * 我的司机
 */

public class SearchDriverPresenter implements BaseView ,SearchDrivierListener {

    private Context mContext;
    SearchDriverModel model;
    SearchDriverView view;


    public SearchDriverPresenter(SearchDriverView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new SearchDriverModelImple();

    }
    public void getcarList(String phone){
        model.getCarList(view,this,phone);

    }


    @Override
    public void showLoading() {
        view.showLoading();

    }

    @Override
    public void dissLoading() {
      view.dissLoading();
    }

    @Override
    public void Toast(String str) {
      view.Toast(str);
    }

    @Override
    public void oncomplete() {

    }

    @Override
    public void callbackbrand(SearchDrivierDto searchCarDtos) {
      view.compelete(searchCarDtos);
    }
}

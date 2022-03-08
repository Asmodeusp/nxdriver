package com.saimawzc.freight.presenter.mine.driver;

import android.content.Context;

import com.saimawzc.freight.dto.my.driver.DriverPageDto;
import com.saimawzc.freight.dto.my.driver.MyDriverDto;
import com.saimawzc.freight.modle.mine.driver.MyDriverModel;
import com.saimawzc.freight.modle.mine.driver.MyDriverModelImple;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.view.mine.driver.MyDriverView;
import com.saimawzc.freight.weight.utils.listen.driver.MyDriverListener;

import java.util.List;

/**
 * Created by Administrator on 2020/8/10.
 */

public class MyDriverPresenter implements BaseView,MyDriverListener {
    private Context mContext;
    MyDriverModel model;
    MyDriverView view;


    public MyDriverPresenter(MyDriverView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MyDriverModelImple();

    }
    public void getcarList(int type,int page,String userAccount){
        model.getDriverList(view,this,type,page,userAccount);

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
        view.stopRefresh();

    }


    @Override
    public void callbackbrand(DriverPageDto myDriverDtos) {
        view.getMyDriverList(myDriverDtos);

    }
}

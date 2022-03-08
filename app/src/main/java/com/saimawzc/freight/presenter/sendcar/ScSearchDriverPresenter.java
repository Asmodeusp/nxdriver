package com.saimawzc.freight.presenter.sendcar;

import android.content.Context;

import com.saimawzc.freight.dto.my.driver.MyDriverDto;
import com.saimawzc.freight.modle.mine.driver.MyDriverModel;
import com.saimawzc.freight.modle.mine.driver.MyDriverModelImple;
import com.saimawzc.freight.modle.sendcar.imple.ScDriverModelImple;
import com.saimawzc.freight.modle.sendcar.model.ScDriverModel;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.view.mine.driver.MyDriverView;
import com.saimawzc.freight.view.sendcar.ScDriverView;
import com.saimawzc.freight.weight.utils.listen.driver.MyDriverListener;

import java.util.List;

/**
 * Created by Administrator on 2020/8/10.
 */

public class ScSearchDriverPresenter implements BaseView {
    private Context mContext;
    ScDriverModel model;
    ScDriverView view;


    public ScSearchDriverPresenter(ScDriverView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ScDriverModelImple();

    }
    public void getcarList(String id,String companyId){
        model.getDriverList(view,id,companyId);

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



}

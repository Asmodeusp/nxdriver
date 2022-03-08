package com.saimawzc.freight.presenter.mine.lessess;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.my.carrier.MyCarrierDto;
import com.saimawzc.freight.dto.my.lessess.LessessPageDto;
import com.saimawzc.freight.dto.my.lessess.MyLessessDto;
import com.saimawzc.freight.modle.mine.carrier.MyCarrierModel;
import com.saimawzc.freight.modle.mine.carrier.MyCarrierModelImple;
import com.saimawzc.freight.modle.mine.lessess.MyLessessModel;
import com.saimawzc.freight.modle.mine.lessess.MyLessessModelImple;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.view.mine.carrier.MyCarrierView;
import com.saimawzc.freight.view.mine.lessess.MyLessessView;
import com.saimawzc.freight.weight.utils.listen.carrier.MyCarrierListener;
import com.saimawzc.freight.weight.utils.listen.lessess.MyLessessListener;

import java.util.List;

/**
 * Created by Administrator on 2020/8/10.
 */

public class MyLessessPresenter implements BaseView,MyLessessListener,BaseListener {
    private Context mContext;
    MyLessessModel model;
    MyLessessView view;
    public MyLessessPresenter(MyLessessView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MyLessessModelImple();
    }

    public void getcarList(int type,int page,String carNo){
        model.getMyLessess(view,this,type,page,carNo);
    }
    public void dealApply(String id,String status,int type){
        model.agreenApply(id,status,view,this,type);
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
    public void callbackbrand(LessessPageDto lessessDtos) {
        view.getLessessList(lessessDtos);
    }
    @Override
    public void successful() {
        view.oncomplete();
    }
    @Override
    public void onFail(String str) {
        view.Toast(str);
    }
    @Override
    public void successful(int type) {

    }
}

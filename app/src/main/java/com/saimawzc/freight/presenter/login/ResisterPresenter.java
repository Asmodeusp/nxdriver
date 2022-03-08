package com.saimawzc.freight.presenter.login;

import android.content.Context;
import android.text.TextUtils;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.modle.login.ResisterModel;
import com.saimawzc.freight.modle.login.imple.ResisterModelImple;
import com.saimawzc.freight.view.login.ResisterView;

/**
 * Created by Administrator on 2020/7/30.
 */

public class ResisterPresenter implements BaseListener {

    private Context mContext;
    ResisterView view;
    ResisterModel model;
    public ResisterPresenter(ResisterView iLoginView, Context context) {
        this.view = iLoginView;
        this.mContext = context;
        model=new ResisterModelImple();
    }
    public void getCode(){
        String phone =view.getPhone();
        if(TextUtils.isEmpty(phone)){
            view.Toast("手机号码不能为空");
            return;
        }
        if(phone.length()!=11){
            view.Toast("手机号码有误");
            return;
        }
        model.getCode(phone,this);
    }
    /****
     * 注册
     * ****/
    public void resister(){
        model.resiser(view,this);
    }
    public void login(String password){
        model.login(view, password,this);
    }
    @Override
    public void successful() {

    }
    @Override
    public void onFail(String str) {
        view.Toast(str);
    }

    @Override
    public void successful(int type) {
        if(type==1){
            view.changeStatus();
        }else {
            view.oncomplete(type);
        }

    }
}

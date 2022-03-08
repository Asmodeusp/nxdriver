package com.saimawzc.freight.presenter.login;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.modle.login.LoginModel;
import com.saimawzc.freight.modle.login.imple.LoginModelImple;
import com.saimawzc.freight.view.login.LoginView;

/**
 * Created by Administrator on 2020/7/30.
 */

public class LoginPresenter implements BaseListener {

    private Context mContext;
    LoginView view;
    LoginModel model;
    public LoginPresenter(LoginView iLoginView, Context context) {
        this.view = iLoginView;
        this.mContext = context;
        model=new LoginModelImple();
    }

    public void login(int role,boolean ischeck){//角色 1货主 2承运商 3司机 4收货人
        model.login(view,this,role,ischeck);
    }

    public void  login(String role,String account,String pass){
        model.login(view,this,account,pass,Integer.parseInt(role) );
    }

    @Override
    public void successful() {
        //view.oncomplete();
    }
    @Override
    public void onFail(String str) {
        view.Toast(str);
    }

    @Override
    public void successful(int type) {

      view.oncomplete(type);
    }
}

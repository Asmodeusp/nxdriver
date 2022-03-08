package com.saimawzc.freight.presenter.login;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.modle.login.VCodeLoginModel;
import com.saimawzc.freight.modle.login.imple.VCodeLoginModelImple;
import com.saimawzc.freight.view.login.VCodeLoginView;

/**
 * Created by Administrator on 2020/7/30.
 * 验证码登录
 */

public class VCodeLoginPresenter implements BaseListener {

    private Context mContext;
    VCodeLoginView view;
    VCodeLoginModel model;
    public VCodeLoginPresenter(VCodeLoginView iLoginView, Context context) {
        this.view = iLoginView;
        this.mContext = context;
        model=new VCodeLoginModelImple();
    }

    public void login(int role){//角色 1货主 2承运商 3司机 4收货人
        model.login(view,this,role);
    }

    public void getCode(){//角色 1货主 2承运商 3司机 4收货人
        model.getCode(view,this);
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
        if(type==1){//获取验证码
            view.changeStatus();
        }else if(type==2||type==3){//登录
            view.oncomplete(type);

        }

    }
}

package com.saimawzc.freight.presenter.login;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.modle.login.ForgetPassModel;
import com.saimawzc.freight.modle.login.imple.ForgetPassModelImple;
import com.saimawzc.freight.view.login.ForgetPassView;

/**
 * Created by Administrator on 2020/7/30.
 * 忘记密码
 */

public class ForgetPassresenter implements BaseListener {

    private Context mContext;
    ForgetPassView view;
    ForgetPassModel model;

    public ForgetPassresenter(ForgetPassView iLoginView, Context context) {
        this.view = iLoginView;
        this.mContext = context;
        model=new ForgetPassModelImple();
    }
    public void getCode(){//角色 1货主 2承运商 3司机 4收货人
        model.getCode(view,this);
    }

    public void forgetPass(){//角色 1货主 2承运商 3司机 4收货人
        model.updatePass(view,this);
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

        }else if(type==2){
            view.oncomplete();
        }


    }
}

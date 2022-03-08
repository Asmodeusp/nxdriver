package com.saimawzc.freight.modle.login;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.view.login.ResisterView;

/**
 * Created by Administrator on 2020/7/30.
 */

public interface ResisterModel {

    void getCode(String phone, final BaseListener listener);


    void resiser(ResisterView view, final BaseListener listener);

    void login(ResisterView view,String passWord, BaseListener listener);
}

package com.saimawzc.freight.modle.login;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.view.login.VCodeLoginView;

/**
 * Created by Administrator on 2020/7/31.
 * 验证码登录
 */

public interface VCodeLoginModel {

    void getCode(VCodeLoginView view, final BaseListener listener);

    void login(VCodeLoginView view, BaseListener listener, int role);


}

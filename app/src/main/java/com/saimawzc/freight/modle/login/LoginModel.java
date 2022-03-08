package com.saimawzc.freight.modle.login;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.view.login.LoginView;

/**
 * Created by Administrator on 2020/7/31.
 */

public interface LoginModel  {


   void login(LoginView view, BaseListener listener,int role,boolean ischeck);

   void login(LoginView view, BaseListener listener,String account,String pass,int role);
}

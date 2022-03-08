package com.saimawzc.freight.view.login;

import android.content.Context;

import com.saimawzc.freight.view.BaseView;

/**
 * Created by Administrator on 2020/7/31.
 */

public interface LoginView extends BaseView {

    String getPhone();
    String getPass();
    Context getContext();
    void oncomplete(int roel);
}

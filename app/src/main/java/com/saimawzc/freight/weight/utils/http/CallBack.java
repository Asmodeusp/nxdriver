package com.saimawzc.freight.weight.utils.http;


import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.ui.login.LoginActivity;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by longbh on 16/5/25.
 */
public abstract class CallBack<T> implements Callback<JsonResult<T>> {

    @Override
    public void onResponse(Call<JsonResult<T>> call, Response<JsonResult<T>>response) {
        JsonResult<T> result = response.body();
        try {
            if (result == null) {
                onFail("", "没有数据");
            } else {
                if (result.isOk()) {
                    success(result.data);
                } else {
                    onFail(result.errCode+"", result.message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onFailure(Call<JsonResult<T>> call, Throwable t) {
        try {
            onFail("","数据连接失败"+t.getMessage());
            t.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void onFail(String code, String message) {
        if(code.equals("2020")||code.equals("2021")){
            Log.e("msg","token异常跳转登录页");
            Hawk.put(PreferenceKey.ID,"");
            Hawk.put(PreferenceKey.USER_INFO,null);
            Hawk.put(PreferenceKey.PERSON_CENTER,null);
            BaseApplication.getInstance().goLoginActivity();
        }
        fail(code, message);
    }
    public abstract void success(T response);
    public abstract void fail(String code, String message);
}

package com.saimawzc.freight.modle.login.imple;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.modle.login.ResisterModel;
import com.saimawzc.freight.view.login.ResisterView;
import com.saimawzc.freight.weight.utils.StringUtil;
import com.saimawzc.freight.weight.utils.api.auto.AuthApi;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/7/30.
 */

public class ResisterModelImple implements ResisterModel {

    AuthApi authApi= Http.http.createApi(AuthApi.class);

    @Override
    public void getCode(String phone, final BaseListener listener) {

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("mobile",phone);
            jsonObject.put("type","register");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        authApi.getCode(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                listener.successful(1);
            }

            @Override
            public void fail(String code, String message) {
             listener.onFail(message);
            }
        });

    }

    @Override
    public void resiser(final ResisterView view, final BaseListener listener) {
        JSONObject jsonObject=new JSONObject();
        view.showLoading();
        try {
            jsonObject.put("createSource","1");//1安卓
            jsonObject.put("loginDevice","1");//登录设备号
            jsonObject.put("password", StringUtil.Md5(view.getPassWord()));

            if(TextUtils.isEmpty(view.resiserType())){
                view.Toast("未获取到注册的角色");
                return;
            }
            if(view.resiserType().contains("司机")){
                jsonObject.put("role",3);
            } else if(view.resiserType().contains("承运商")){
                jsonObject.put("role",2);
            }
            jsonObject.put("userAccount",view.getPhone());
            jsonObject.put("verificationCode",view.getYzm());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        authApi.resister(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                listener.successful(100);
                view.Toast("注册成功");
                view.dissLoading();
            }
            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
                view.dissLoading();
            }
        });
    }

    @Override
    public void login(final ResisterView view, String pass ,final BaseListener listener) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("loginDevice","");
            jsonObject.put("loginSource","1");//安卓

            jsonObject.put("password",StringUtil.Md5(pass));
            if(view.resiserType().contains("司机")){
                jsonObject.put("role",3);
            }
            if(view.resiserType().contains("承运商")){
                jsonObject.put("role",2);
            }
            jsonObject.put("userAccount",view.getPhone());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        authApi.login(body).enqueue(new CallBack<UserInfoDto>() {
            @Override
            public void success(UserInfoDto response) {
                view.dissLoading();
                int role=0;
                if(view.resiserType().contains("司机")){
                    role=3;
                }
                if(view.resiserType().contains("承运商")){
                    role=2;
                }
                Hawk.put(PreferenceKey.USER_INFO,response);
                Hawk.put(PreferenceKey.LOGIN_TYPE,role);
                Hawk.put(PreferenceKey.ID,response.getToken());

                listener.successful(role);
            }

            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
                view.dissLoading();
            }
        });
    }
}

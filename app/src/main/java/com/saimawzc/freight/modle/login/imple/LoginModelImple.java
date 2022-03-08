package com.saimawzc.freight.modle.login.imple;

import android.app.Activity;
import android.support.annotation.UiThread;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.login.LoginModel;
import com.saimawzc.freight.view.login.LoginView;
import com.saimawzc.freight.weight.utils.StringUtil;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.saimawzc.freight.weight.utils.sp.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/7/31.
 * 登录实现
 */

public class LoginModelImple extends BasEModeImple implements LoginModel {

    @Override
    public void login(final LoginView view, final BaseListener listener, final int role,final boolean ischeck) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("loginDevice","");
            jsonObject.put("loginSource","1");//安卓
            jsonObject.put("password", StringUtil.Md5(view.getPass()));
            jsonObject.put("role",role);
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
                Hawk.put(PreferenceKey.USER_INFO,response);
                Hawk.put(PreferenceKey.LOGIN_TYPE,role);
                Hawk.put(PreferenceKey.ID,response.getToken());
                Hawk.put(PreferenceKey.Fingerprint_Account,view.getPhone());
                Hawk.put(PreferenceKey.Fingerprint_Pass,view.getPass());
                Hawk.put(PreferenceKey.Fingerprint_Type,role+"");
                if(ischeck){
                    Hawk.put(PreferenceKey.USER_ACCOUNT,view.getPhone());
                    Hawk.put(PreferenceKey.PASS_WORD,view.getPass());
                    Hawk.put(PreferenceKey.ISREMEMBER_PASS,"1");
                }else {
                    Hawk.put(PreferenceKey.USER_ACCOUNT,"");
                    Hawk.put(PreferenceKey.PASS_WORD,"");
                    Hawk.put(PreferenceKey.ISREMEMBER_PASS,"");
                }

                ((Activity)view.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Http.initHttp(view.getContext());
                    }
                });

                listener.successful(role);
            }

            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
                view.dissLoading();
            }
        });

    }

    @Override
    public void login(final LoginView view, final BaseListener listener, final String account, final String pass, final int role) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("loginDevice","");
            jsonObject.put("loginSource","1");//安卓
            jsonObject.put("password", StringUtil.Md5(pass));
            jsonObject.put("role",role);
            jsonObject.put("userAccount",account);
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
                Hawk.put(PreferenceKey.USER_INFO,response);
                Hawk.put(PreferenceKey.LOGIN_TYPE,role);
                Hawk.put(PreferenceKey.ID,response.getToken());


                Hawk.put(PreferenceKey.USER_ACCOUNT,account);
                Hawk.put(PreferenceKey.PASS_WORD,pass);
                Hawk.put(PreferenceKey.ISREMEMBER_PASS,"1");


                ((Activity)view.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Http.initHttp(view.getContext());
                    }
                });

                listener.successful(role);
                String  channelId = (String) SPUtils.get(BaseApplication.getInstance(), "channelId", "");
                submitPushInfo(channelId);
            }

            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
                view.dissLoading();
            }
        });
    }
    private void submitPushInfo(String channelId){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("loginSource","1");//安卓
            jsonObject.put("channelId",channelId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        authApi.updatePushInfo(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {

            }
            @Override
            public void fail(String code, String message) {

            }
        });
    }

}

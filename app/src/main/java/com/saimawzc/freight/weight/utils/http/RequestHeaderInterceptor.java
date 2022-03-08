package com.saimawzc.freight.weight.utils.http;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.ui.MainActivity;
import com.saimawzc.freight.ui.login.LoginActivity;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;


/**
 * Created by zhangdm on 2016/4/20.
 */
public class RequestHeaderInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        String token = Hawk.get(PreferenceKey.ID,"");
        Request originalRequest = chain.request();//旧链接
        Request.Builder requestBuilder = originalRequest.newBuilder()
                    .addHeader("WZCTK", token);
        Request request = requestBuilder.build();
        return chain.proceed(request);


    }
}
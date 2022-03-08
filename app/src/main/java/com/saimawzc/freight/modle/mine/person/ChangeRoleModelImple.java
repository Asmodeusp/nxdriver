package com.saimawzc.freight.modle.mine.person;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.mine.person.ChangeRoleView;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by Administrator on 2020/8/13.
 */

public class ChangeRoleModelImple extends BasEModeImple implements ChangeRoleModel {

    @Override
    public void changeRole(final ChangeRoleView view, final BaseListener listener, final int role) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("role",role+"");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        mineApi.changeRole(body).enqueue(new CallBack<UserInfoDto>() {
            @Override
            public void success(UserInfoDto response) {
                view.dissLoading();

                Hawk.remove(PreferenceKey.ID);
                Hawk.remove(PreferenceKey.USER_INFO);
                Hawk.put(PreferenceKey.ID,response.getToken());
                Hawk.put(PreferenceKey.LOGIN_TYPE,role);
                Hawk.put(PreferenceKey.USER_INFO,response);
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
}

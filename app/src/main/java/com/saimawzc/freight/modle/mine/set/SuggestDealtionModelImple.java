package com.saimawzc.freight.modle.mine.set;

import android.content.Context;
import android.util.Log;
import com.saimawzc.freight.dto.my.set.SuggestDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.mine.set.MySuggestDelationView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Administrator on 2020/8/13.
 */

public class SuggestDealtionModelImple extends BasEModeImple implements SuggestDelationModel {

    @Override
    public void getSuggestDelaiton(final MySuggestDelationView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        mineApi.mySuggesDelarion(body).enqueue(new CallBack<SuggestDto>() {
            @Override
            public void success(SuggestDto response) {
                view.dissLoading();
                view.getSuggestDelation(response);
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

}

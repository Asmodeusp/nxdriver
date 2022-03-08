package com.saimawzc.freight.modle.sendcar.imple;

import android.content.Context;
import android.text.TextUtils;


import com.saimawzc.freight.dto.sendcar.ArriverOrderDto;
import com.saimawzc.freight.dto.sendcar.LogistoicDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.sendcar.model.LogistocModel;
import com.saimawzc.freight.view.sendcar.LogistoicView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LogistoicModelImple extends BasEModeImple implements LogistocModel {


    @Override
    public void getData(final LogistoicView view, String id,String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            if(!TextUtils.isEmpty(type)){
                if(type.equals("cys")){
                    jsonObject.put("wayBillId",id);
                }else {
                    jsonObject.put("id",id);
                }
            }else {
                jsonObject.put("id",id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getLogistoc(body).enqueue(new CallBack<List<LogistoicDto>>() {
            @Override
            public void success(List<LogistoicDto> response) {
                view.dissLoading();
                view.getData(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

package com.saimawzc.freight.modle.sendcar.imple;

import android.text.TextUtils;
import com.saimawzc.freight.dto.sendcar.WarnInfoDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.sendcar.model.WarnInfoModel;
import com.saimawzc.freight.view.sendcar.WarnInfoView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class WarnInfoModelImple extends BasEModeImple implements WarnInfoModel {


    @Override
    public void getData(final WarnInfoView view, String id, String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            if(!TextUtils.isEmpty(type)){
                if(type.equals("cys")){
                    jsonObject.put("config",1);
                }else {
                    jsonObject.put("config",2);
                }
            }else {
                jsonObject.put("config",2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getWarnInfo(body).enqueue(new CallBack<List<WarnInfoDto>>() {
            @Override
            public void success(List<WarnInfoDto> response) {
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

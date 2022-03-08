package com.saimawzc.freight.modle.order.modelImple;
import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.order.StopTrantDelationDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.bidd.StopTrantDelationModel;
import com.saimawzc.freight.modle.order.modle.bidd.StopTrantModel;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.view.order.StopTrantDelationView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class StopTrantDelationModelImple extends BasEModeImple implements StopTrantDelationModel {

    @Override
    public void stopTrantDelation(final StopTrantDelationView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getStoptrantdelation(body).enqueue(new CallBack<StopTrantDelationDto>() {
            @Override
            public void success(StopTrantDelationDto response) {
                view.dissLoading();
                view.getDtlation(response);

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

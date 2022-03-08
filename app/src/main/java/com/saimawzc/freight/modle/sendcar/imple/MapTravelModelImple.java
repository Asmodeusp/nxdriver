package com.saimawzc.freight.modle.sendcar.imple;

import com.saimawzc.freight.dto.my.RouteDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.sendcar.model.MapTravelModel;
import com.saimawzc.freight.view.sendcar.MapTravelView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
public class MapTravelModelImple extends BasEModeImple implements MapTravelModel {


    @Override
    public void roulete(final MapTravelView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getRoute(body).enqueue(new CallBack<RouteDto>() {
            @Override
            public void success(RouteDto response) {
                view.dissLoading();
                view.getRolte(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

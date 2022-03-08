package com.saimawzc.freight.modle.travel.imple;
import com.saimawzc.freight.dto.travel.BeidouTravelDto;
import com.saimawzc.freight.dto.travel.PresupTravelDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.travel.TravelModel;
import com.saimawzc.freight.view.travel.TravelView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TravelModelImple extends BasEModeImple implements TravelModel {

    @Override
    public void getBeiDouTravel(final TravelView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("config",1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getBeiDouTravel(body).enqueue(new CallBack<BeidouTravelDto>() {
            @Override
            public void success(BeidouTravelDto response) {
                view.dissLoading();
                view.getBeiDouTravel(response);

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.getBeiDouTravel(null);
                //view.Toast(message);
            }
        });
    }

    @Override
    public void getPreSupTravel(final TravelView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("config",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getPreSupTravel(body).enqueue(new CallBack<PresupTravelDto>() {
            @Override
            public void success(PresupTravelDto response) {
                view.dissLoading();
                view.getPreSupTravel(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });

    }
}

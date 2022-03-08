package com.saimawzc.freight.modle.order.modelImple;
import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.order.AddWayBillDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.AddWayBillModel;
import com.saimawzc.freight.modle.order.modle.bidd.StopTrantModel;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.view.order.AddwaybillView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class StopTrantModelImple extends BasEModeImple implements StopTrantModel {

    @Override
    public void stopTrant(final BaseView view, final BaseListener listener, String id,String reason) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("offOpinion",reason);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.stopTrant(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                listener.successful();
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

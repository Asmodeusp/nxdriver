package com.saimawzc.freight.modle.order.modelImple;



import com.saimawzc.freight.dto.order.OrderManageRoleDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.ManagwMapModel;
import com.saimawzc.freight.view.order.OrderManageMapView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.order.OrderManageMapListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ManagwMapModelImple extends BasEModeImple implements ManagwMapModel {
    @Override
    public void getOrderManageList(final OrderManageMapView view, final OrderManageMapListener listener,
                                   String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getRuleList(body).enqueue(new CallBack<OrderManageRoleDto>() {
            @Override
            public void success(OrderManageRoleDto response) {
                view.dissLoading();
                listener.back(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

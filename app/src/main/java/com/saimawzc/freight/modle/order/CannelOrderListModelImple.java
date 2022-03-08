package com.saimawzc.freight.modle.order;

import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.order.CancelOrderDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.CancelOrderListModel;
import com.saimawzc.freight.view.order.CancelOrderListView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CannelOrderListModelImple extends BasEModeImple implements CancelOrderListModel {



    @Override
    public void getListData(final CancelOrderListView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getCancelList(body).enqueue(new CallBack<List<CancelOrderDto>>() {
            @Override
            public void success(List<CancelOrderDto> respon) {
                view.dissLoading();
                view.getList(respon);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void shOrder(final CancelOrderListView view, String id, int Status) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("status",Status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.applyCancelOrder(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto respon) {
                view.dissLoading();
                view.oncomplete();
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

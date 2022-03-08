package com.saimawzc.freight.modle.order.modelImple;


import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.order.ExamGoodDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.ExamGoodModel;
import com.saimawzc.freight.view.order.ExamGoodView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ExamGoodModelImple extends BasEModeImple implements ExamGoodModel {


    @Override
    public void getExamGood(final ExamGoodView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getExamGoods(body).enqueue(new CallBack<List<ExamGoodDto>>() {
            @Override
            public void success(List<ExamGoodDto> response) {
                view.dissLoading();
                view.getExamList(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

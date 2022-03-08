package com.saimawzc.freight.modle.mine.lessess;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.my.carrier.CarrierPageDto;
import com.saimawzc.freight.dto.my.lessess.LessessPageDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.mine.carrier.MyCarrierModel;
import com.saimawzc.freight.view.mine.carrier.MyCarrierView;
import com.saimawzc.freight.view.mine.lessess.MyLessessView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.carrier.MyCarrierListener;
import com.saimawzc.freight.weight.utils.listen.lessess.MyLessessListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/10.
 * 我的司机
 */

public class MyLessessModelImple extends BasEModeImple implements MyLessessModel {
    @Override
    public void getMyLessess(final MyLessessView view,final MyLessessListener listener,
                             int type, int page,String carNo) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            jsonObject.put("pageSize",20);
            jsonObject.put("checkStatus",type);//state状态 （1.以添加 2.待确认）
            jsonObject.put("carNo",carNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getLessessList(body).enqueue(new CallBack<LessessPageDto>() {
            @Override
            public void success(LessessPageDto response) {
                view.dissLoading();
                listener.callbackbrand(response);
                view.stopRefresh();
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
                view.stopRefresh();
            }
        });
    }

    @Override
    public void agreenApply(String id, String status, final MyLessessView view,
                            final BaseListener listener,int type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("checkStatus",status);
            jsonObject.put("type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.agreen(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                listener.successful();

            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                listener.onFail(message);
            }
        });
    }
}

package com.saimawzc.freight.modle.mine.mysetment;


import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.account.WaitComfirmAccountDto;
import com.saimawzc.freight.dto.account.WaitComfirmQueryPageDto;
import com.saimawzc.freight.dto.account.WaitDispatchDto;
import com.saimawzc.freight.dto.account.WaitDispatchQueryPageDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.mine.setment.WaitSetmentSmallOrderView;
import com.saimawzc.freight.view.mine.setment.WaitSetmentView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/3.
 */

public class WaitSetmentSmallOrderModelImple extends BasEModeImple implements WaitSetmentSmallOrderModel {

    @Override
    public void getList(int page,String id, final WaitSetmentSmallOrderView view) {

        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            jsonObject.put("planWayBillId",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.getsmallOrderList(body).enqueue(new CallBack<WaitDispatchQueryPageDto>() {
            @Override
            public void success(WaitDispatchQueryPageDto response) {
                view.dissLoading();
                view.getData(response);
                view.stopResh();
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
                view.stopResh();
            }
        });

    }



    @Override
    public void addsetment(List<String> idList, final WaitSetmentSmallOrderView view) {

        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
               // String[] array =new String[idList.size()];
                jsonObject.put("wayBillIdList",new JSONArray(idList));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.addSetment(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
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

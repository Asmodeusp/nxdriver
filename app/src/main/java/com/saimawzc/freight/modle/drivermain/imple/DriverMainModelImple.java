package com.saimawzc.freight.modle.drivermain.imple;

import android.util.Log;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.order.NeedOpenFenceDto;
import com.saimawzc.freight.dto.order.mainindex.RobOrderDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.drivermain.DriverMainModel;
import com.saimawzc.freight.modle.login.ForgetPassModel;
import com.saimawzc.freight.view.drivermain.DriverMainView;
import com.saimawzc.freight.view.login.ForgetPassView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/7/31.
 * 忘记密码
 */

public class DriverMainModelImple extends BasEModeImple implements DriverMainModel {



    @Override
    public void getNeedFence(final DriverMainView view) {
        JSONObject jsonObject=new JSONObject();
        view.showLoading();
        try {
            jsonObject.put("","");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        tmsApi.getNeedFenceList(body).enqueue(new CallBack<NeedOpenFenceDto>() {
            @Override
            public void success(NeedOpenFenceDto response) {
                view.dissLoading();
                view.getNeedFence(response);
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.getNeedFence(null);
            }
        });
    }

    @Override
    public void getRobLsit(final DriverMainView view, int page) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getsjRobData(body).enqueue(new CallBack<RobOrderDto>() {
            @Override
            public void success(RobOrderDto response) {
                view.dissLoading();
                view.isLastPage(response.isLastPage());
                view.getPlanOrderList(response.getList());
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

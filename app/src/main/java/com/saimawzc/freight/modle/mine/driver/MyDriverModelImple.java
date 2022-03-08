package com.saimawzc.freight.modle.mine.driver;

import com.saimawzc.freight.dto.my.driver.DriverPageDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.mine.driver.MyDriverView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.driver.MyDriverListener;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/10.
 * 我的司机
 */

public class MyDriverModelImple extends BasEModeImple implements MyDriverModel {



    @Override
    public void getDriverList(final MyDriverView view, final MyDriverListener listener,
                              int type, int page,String userAccount) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            jsonObject.put("pageSize",20);
            jsonObject.put("userAccount",userAccount);
            jsonObject.put("state",type);//state状态 （1.以添加 2.待确认）
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getMyDriver(body).enqueue(new CallBack<DriverPageDto>() {
            @Override
            public void success(DriverPageDto response) {
                view.dissLoading();
                view.stopRefresh();
                listener.callbackbrand(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
                view.stopRefresh();
            }
        });
    }
}

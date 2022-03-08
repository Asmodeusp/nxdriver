package com.saimawzc.freight.modle.mine.car.ship;

import android.util.Log;

import com.saimawzc.freight.dto.my.car.ship.MyShipDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.mine.car.ship.MyShipView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/8.
 */

public class MyChangeShipModelImple extends BasEModeImple implements MyShipModel {

    @Override
    public void getCarList(final MyShipView view, int type, int page,String carNo) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("status",type);
            jsonObject.put("pageNum",page);
            jsonObject.put("pageSize",20);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getShipChange(body).enqueue(new CallBack<MyShipDto>() {
            @Override
            public void success(MyShipDto response) {
                view.stopRefresh();
                view.dissLoading();
                view.compelete(response);
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

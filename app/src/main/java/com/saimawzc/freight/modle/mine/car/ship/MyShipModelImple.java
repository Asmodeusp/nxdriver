package com.saimawzc.freight.modle.mine.car.ship;

import com.saimawzc.freight.dto.my.car.ship.MyShipDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.mine.car.ship.MyShipModel;
import com.saimawzc.freight.view.mine.car.ship.MyShipView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/8.
 */

public class MyShipModelImple extends BasEModeImple implements MyShipModel {




    @Override
    public void getCarList(final MyShipView view, int type, int page,String shipNo) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("checkStatus",type);
            jsonObject.put("pageNum",page);
            jsonObject.put("pageSize",20);
            jsonObject.put("carNo",shipNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getMyShipList(body).enqueue(new CallBack<MyShipDto>() {
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

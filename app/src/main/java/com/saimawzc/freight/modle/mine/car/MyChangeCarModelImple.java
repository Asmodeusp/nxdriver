package com.saimawzc.freight.modle.mine.car;

import android.util.Log;

import com.saimawzc.freight.dto.my.car.MyCarDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.mine.car.MyCarView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.car.SearchCarListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/8.
 */

public class MyChangeCarModelImple extends BasEModeImple implements MyChangeCarModel {

    @Override
    public void getCarList(final MyCarView view,
                           final SearchCarListener listener, int type,int page) {
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
        mineApi.getCarChange(body).enqueue(new CallBack<MyCarDto>() {
            @Override
            public void success(MyCarDto response) {
                view.stopRefresh();
                view.dissLoading();
                Log.e("msg","获取长度"+response.getList());
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

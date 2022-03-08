package com.saimawzc.freight.modle.mine.driver;

import com.saimawzc.freight.dto.my.driver.SearchDrivierDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.mine.driver.SearchDriverView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.driver.SearchDrivierListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/10.
 * 我的司机
 */

public class SearchDriverModelImple extends BasEModeImple implements SearchDriverModel {


    @Override
    public void getCarList(final SearchDriverView view, final SearchDrivierListener listener, String phone) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("phone",phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.searchDriver(body).enqueue(new CallBack<SearchDrivierDto>() {
            @Override
            public void success(SearchDrivierDto response) {
                view.dissLoading();
                listener.callbackbrand(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });

    }
}

package com.saimawzc.freight.modle.mine.car.ship;

import com.saimawzc.freight.dto.my.car.ship.SearchShipDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.mine.car.ship.SearchShipModel;
import com.saimawzc.freight.view.mine.car.ship.SearchShipView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/8.
 * 搜索车辆
 */

public class SearchShipModelImple extends BasEModeImple implements SearchShipModel {

    @Override
    public void getShipList(final SearchShipView view,String key) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("shipName",key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getSearchShipList(body).enqueue(new CallBack<List<SearchShipDto>>() {
            @Override
            public void success(List<SearchShipDto> response) {
                view.dissLoading();
                view.compelete(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

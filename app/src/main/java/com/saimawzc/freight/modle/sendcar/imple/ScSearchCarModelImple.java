package com.saimawzc.freight.modle.sendcar.imple;

import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.mine.car.SearchCarModel;
import com.saimawzc.freight.view.mine.car.SearchCarView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.car.SearchCarListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/8.
 * 搜索车辆
 */

public class ScSearchCarModelImple extends BasEModeImple implements SearchCarModel {


    @Override
    public void getCarList(final SearchCarView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("carNo",view.getCarNum());
            jsonObject.put("companyId",view.getCompanyId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getSearchCarList(body).enqueue(new CallBack<List<SearchCarDto>>() {
            @Override
            public void success(List<SearchCarDto> response) {
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

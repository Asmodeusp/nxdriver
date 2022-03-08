package com.saimawzc.freight.modle.sendcar.imple;

import com.saimawzc.freight.dto.my.driver.DriverPageDto;
import com.saimawzc.freight.dto.sendcar.ScSearchDriverDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.mine.driver.MyDriverModel;
import com.saimawzc.freight.modle.sendcar.model.ScDriverModel;
import com.saimawzc.freight.view.mine.driver.MyDriverView;
import com.saimawzc.freight.view.sendcar.ScDriverView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.driver.MyDriverListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/10.
 * 我的司机
 */

public class ScDriverModelImple extends BasEModeImple implements ScDriverModel {





    @Override
    public void getDriverList(final ScDriverView view, String id,String companyId) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("roleId",id);
            jsonObject.put("phone",view.getPhone());
            jsonObject.put("companyId",companyId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getScDriverList(body).enqueue(new CallBack<List<ScSearchDriverDto>>() {
            @Override
            public void success(List<ScSearchDriverDto> response) {
                view.dissLoading();
                view.stopRefresh();
                view.getDriverList(response);
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

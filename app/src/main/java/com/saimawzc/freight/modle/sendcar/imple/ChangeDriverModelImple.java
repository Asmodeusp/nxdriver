package com.saimawzc.freight.modle.sendcar.imple;

import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.dto.my.driver.MyDriverDto;
import com.saimawzc.freight.dto.sendcar.ScSearchDriverDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.sendcar.model.ChangeCarModel;
import com.saimawzc.freight.modle.sendcar.model.ChangeDriverModel;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ChangeDriverModelImple extends BasEModeImple implements ChangeDriverModel {




    @Override
    public void changeDriver(final BaseView view, String id, ScSearchDriverDto myDriverDto, String reason) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("dispatchCarId",id);
            jsonObject.put("newSjId",myDriverDto.getId());
            jsonObject.put("newSjName",myDriverDto.getSjName());
            jsonObject.put("phone",myDriverDto.getPhone());
            jsonObject.put("sjCode",myDriverDto.getSjCode());
            jsonObject.put("reason", reason);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.changedriver(body).enqueue(new CallBack<EmptyDto>() {
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

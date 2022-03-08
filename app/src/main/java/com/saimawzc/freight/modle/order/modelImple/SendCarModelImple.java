package com.saimawzc.freight.modle.order.modelImple;
import android.text.TextUtils;

import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.order.CarInfolDto;
import com.saimawzc.freight.dto.order.CarModelDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.SendCarModel;
import com.saimawzc.freight.view.order.SendCarView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.order.SendCarInfolListen;
import com.saimawzc.freight.weight.utils.listen.order.SendCarModellListen;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SendCarModelImple extends BasEModeImple implements SendCarModel {






    @Override
    public void getCarModel(final SendCarView view, final SendCarModellListen listener,int trantType) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("type",trantType);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getCarModel(body).enqueue(new CallBack<List<CarModelDto>>() {
            @Override
            public void success(List<CarModelDto> response) {
                view.dissLoading();
                listener.back(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void getCarInfo(final SendCarView view, final SendCarInfolListen listener,
                           int page, String carTypeId,String saerch,int trantType, String companyId) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            jsonObject.put("driverType",trantType);
            if(TextUtils.isEmpty(saerch)){
                jsonObject.put("carTypeId",carTypeId);
            }else {
                jsonObject.put("carNo",saerch);
            }
            if(!TextUtils.isEmpty(companyId)){
                jsonObject.put("companyId",companyId);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getCarInfo(body).enqueue(new CallBack<CarInfolDto>() {
            @Override
            public void success(final CarInfolDto response) {
                view.dissLoading();
                view.isLastPage(response.isLastPage());
                listener.backinfo(response.getList());

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });

    }

    @Override
    public void getsjCarInfo(final SendCarView view, final SendCarInfolListen listener, int page, String carTypeId, String saerch, int trantType,String companyId) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            jsonObject.put("driverType",trantType);
            if(TextUtils.isEmpty(saerch)){
                jsonObject.put("carTypeId",carTypeId);
            }else {
                jsonObject.put("carNo",saerch);
            }
            if(!TextUtils.isEmpty(companyId)){
                jsonObject.put("companyId",companyId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getsjCarInfo(body).enqueue(new CallBack<CarInfolDto>() {
            @Override
            public void success(final CarInfolDto response) {
                view.dissLoading();
                view.isLastPage(response.isLastPage());
                listener.backinfo(response.getList());

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });

    }

    /***
     * 司机派车
     * **/
    @Override
    public void sendCar(final SendCarView view, CarInfolDto.carInfoData carInfoData, String type, String billId, String sendCarNum) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("carId",carInfoData.getId());
            jsonObject.put("carTypeId",carInfoData.getCarTypeId());
            jsonObject.put("carTypeName",carInfoData.getCarTypeName());
            jsonObject.put("carNo",carInfoData.getCarNo());
            jsonObject.put("type",type);// 1.计划单派车 2.预运单 3.调度单派车
            jsonObject.put("id",billId);
            if(!TextUtils.isEmpty(sendCarNum)){
                jsonObject.put("num",sendCarNum);
            }
            if(type.equals("1")){
                jsonObject.put("totalWeight",carInfoData.getYutiNum());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.sjsendCar(body).enqueue(new CallBack<EmptyDto>() {
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

    @Override
    public void carIsHasBeiDou(final SendCarView view, String type, String id, String carId, String carNo) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("type",type);
            jsonObject.put("id",id);
            jsonObject.put("carId",carId);
            jsonObject.put("carNo",carNo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.carIsBeiDou(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                view.ishaveBeiDou(true);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
                view.ishaveBeiDou(false);
            }
        });
    }
}

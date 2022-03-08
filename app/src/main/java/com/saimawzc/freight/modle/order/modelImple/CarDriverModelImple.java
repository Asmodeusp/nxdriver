package com.saimawzc.freight.modle.order.modelImple;
import android.text.TextUtils;
import android.util.Log;

import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.order.CarDriverDto;
import com.saimawzc.freight.dto.order.CarDriverForPage;
import com.saimawzc.freight.dto.order.CarInfolDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.CarDriverModel;
import com.saimawzc.freight.ui.login.LoginActivity;
import com.saimawzc.freight.ui.my.pubandservice.PublisherActivity;
import com.saimawzc.freight.view.order.SendDriverView;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.order.CarDriverListener;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CarDriverModelImple extends BasEModeImple implements CarDriverModel {


    private NormalDialog dialog;

    @Override
    public void getCarDriverList(final SendDriverView view, final
    CarDriverListener listener,String phone,int trantType,String carId,String companyId) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("phone",phone);
            jsonObject.put("driverType",trantType);
            if(!TextUtils.isEmpty(carId)){
                jsonObject.put("carId",carId);
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
        orderApi.getDriver(body).enqueue(new CallBack<List<CarDriverDto>>() {
            @Override
            public void success(List<CarDriverDto> response) {
                view.dissLoading();
                listener.getManageOrderList(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void getDriverListByPage(final SendDriverView view, final CarDriverListener listener, String phone, int trantType, String carId, int page,String companyId) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("phone",phone);
            jsonObject.put("driverType",trantType);
            if(!TextUtils.isEmpty(carId)){
                jsonObject.put("carId",carId);
            }
            if(!TextUtils.isEmpty(companyId)){
                jsonObject.put("companyId",companyId);
            }
            jsonObject.put("pageNum",page);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getDriverByPage(body).enqueue(new CallBack<CarDriverForPage>() {
            @Override
            public void success(CarDriverForPage response) {
                view.dissLoading();
                listener.getManageOrderList(response.getList());
                view.isLastPage(response.isLastPage);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void sendCar(final SendDriverView view, final CarDriverListener listener,
                        CarInfolDto.carInfoData carInfoData, CarDriverDto driverDto,String type,
                        String billId,String sendCarNum) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("carId",carInfoData.getId());
            jsonObject.put("carTypeId",carInfoData.getCarTypeId());
            jsonObject.put("carTypeName",carInfoData.getCarTypeName());
            jsonObject.put("carNo",carInfoData.getCarNo());
            jsonObject.put("sjId",driverDto.getId());
            jsonObject.put("sjCode",driverDto.getSjCode());
            jsonObject.put("sjName",driverDto.getSjName());
            jsonObject.put("sjPhone",driverDto.getUserAccount());
            jsonObject.put("type",type);// 1.计划单派车 2.预运单 3.调度单派车
            jsonObject.put("id",billId);
            if(!TextUtils.isEmpty(sendCarNum)){
                jsonObject.put("num",sendCarNum);
            }

            //jsonObject.put("shipName",shipName);
            if(type.equals("1")){
                jsonObject.put("totalWeight",carInfoData.getYutiNum());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.sendCar(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                listener.successful();
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
                if(code.equals("2055")){
                    if(view.getContect()!=null){
                        dialog = new NormalDialog(view.getContect()).isTitleShow(false)
                                .content("您还未完善发布方信息，是否立即完善?")
                                .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                                .btnNum(2).btnText("取消", "确定");
                        dialog.setOnBtnClickL(
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        if(!view.getContect().isDestroy(view.getContect())){
                                            dialog.dismiss();
                                        }
                                    }
                                },
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        view.getContect().readyGo(PublisherActivity.class);
                                        if(!view.getContect().isDestroy(view.getContect())){
                                            dialog.dismiss();
                                        }
                                    }
                                });
                        dialog.show();

                    }


                }
            }
        });
    }

    @Override
    public void sendsjCar(final SendDriverView view,
                          final CarDriverListener listener, CarInfolDto.carInfoData carInfoData, CarDriverDto driverDto, String type, String billId, String sendCarNum) {
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
            //jsonObject.put("shipName",shipName);
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
                listener.successful();
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

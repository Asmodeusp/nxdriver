package com.saimawzc.freight.modle.mine.identification;

import android.content.Context;
import android.util.Log;

import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.base.CameraListener;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.dto.my.identification.CarrierIndenditicationDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.mine.identificaion.NomalTaxesCarriverView;
import com.saimawzc.freight.view.mine.identificaion.PersonCarrierView;
import com.saimawzc.freight.weight.utils.CameraDialogUtil;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.identification.CarriverIdentificationListener;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/3.
 * 一般纳税人
 */

public class NomalCarrierModelImple extends BasEModeImple implements NomalCarrierModel {

    @Override
    public void reidentification(final NomalTaxesCarriverView view, final BaseListener listener) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        UserInfoDto loginDo= Hawk.get(PreferenceKey.USER_INFO);
        if(loginDo==null){
            listener.onFail("用户信息为空");
            return;
        }
        try {

            jsonObject.put("address",view.getArea());
            jsonObject.put("businessLicense",view.sringBussiselices());//营业执照
            jsonObject.put("businessNum",view.getBusissNum());
            if(view.getzzType().equals("长期")){
                jsonObject.put("businessStatus",2);
            }else {
                jsonObject.put("businessStatus",1);
                jsonObject.put("businessTime",view.getzzType());
            }
            jsonObject.put("carrierType","2");//承运商类型（1.个人承运商 2.一般纳税人 3.小规模企业）
            jsonObject.put("recordCard",view.sringnomalTaxe());//备案证
            jsonObject.put("frontCorporateIdCard",view.sringFrIdcard());//法人身份证
            jsonObject.put("frontIdCard",view.sringImgPositive());//身份证正面
            jsonObject.put("idCardNum",view.getIdNum());
            jsonObject.put("legalPerson",view.getIegalName());
            jsonObject.put("personIdCard",view.getIegalCardNum());
            jsonObject.put("powerAttorney",view.sringEntrust());//委托书
            jsonObject.put("reverseIdCard",view.sringImgOtherSide());//身份证反面
            jsonObject.put("roadNum",view.getRoadNum());
            jsonObject.put("roadPermit",view.sringTransport());//
            jsonObject.put("userAccount",loginDo.getUserAccount());
            jsonObject.put("userCode",loginDo.getUserCode());
            jsonObject.put("userId",loginDo.getId());
            jsonObject.put("userName",view.getUser());
            jsonObject.put("userSource","Android");
            jsonObject.put("companyName",view.getCompanyName());

            jsonObject.put("province",view.proviceName());
            jsonObject.put("provinceId",view.proviceId());
            jsonObject.put("city",view.cityName());
            jsonObject.put("cityId",view.cityId());
            jsonObject.put("area",view.countryName());
            jsonObject.put("areaId",view.countryId());

            jsonObject.put("invoiceMaxAmount",view.invoiceMaxAmount());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        mineApi.recysInentification(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                listener.successful();
                view.dissLoading();

            }
            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
                view.dissLoading();
            }
        });
    }

    private CameraDialogUtil cameraDialogUtil;

    @Override
    public void identification(final NomalTaxesCarriverView view, final BaseListener listener) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        UserInfoDto loginDo= Hawk.get(PreferenceKey.USER_INFO);
        if(loginDo==null){
            listener.onFail("用户信息为空");
            return;
        }
        try {

            jsonObject.put("address",view.getArea());
            jsonObject.put("businessLicense",view.sringBussiselices());//营业执照
            jsonObject.put("businessNum",view.getBusissNum());
            if(view.getzzType().equals("长期")){
                jsonObject.put("businessStatus",2);
            }else {
                jsonObject.put("businessStatus",1);
                jsonObject.put("businessTime",view.getzzType());
            }
            jsonObject.put("carrierType","2");//承运商类型（1.个人承运商 2.一般纳税人 3.小规模企业）
            jsonObject.put("recordCard",view.sringnomalTaxe());//备案证
            jsonObject.put("frontCorporateIdCard",view.sringFrIdcard());//法人身份证
            jsonObject.put("frontIdCard",view.sringImgPositive());//身份证正面
            jsonObject.put("idCardNum",view.getIdNum());
            jsonObject.put("legalPerson",view.getIegalName());
            jsonObject.put("personIdCard",view.getIegalCardNum());
            jsonObject.put("powerAttorney",view.sringEntrust());//委托书
            jsonObject.put("reverseIdCard",view.sringImgOtherSide());//身份证反面
            jsonObject.put("roadNum",view.getRoadNum());
            jsonObject.put("roadPermit",view.sringTransport());//
            jsonObject.put("userAccount",loginDo.getUserAccount());
            jsonObject.put("userCode",loginDo.getUserCode());
            jsonObject.put("userId",loginDo.getId());
            jsonObject.put("userName",view.getUser());
            jsonObject.put("userSource","Android");
            jsonObject.put("companyName",view.getCompanyName());

            jsonObject.put("province",view.proviceName());
            jsonObject.put("provinceId",view.proviceId());
            jsonObject.put("city",view.cityName());
            jsonObject.put("cityId",view.cityId());
            jsonObject.put("area",view.countryName());
            jsonObject.put("areaId",view.countryId());
            jsonObject.put("invoiceMaxAmount",view.invoiceMaxAmount());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        mineApi.cysInentification(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                listener.successful();
                view.dissLoading();

            }
            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
                view.dissLoading();
            }
        });
    }

    @Override
    public void showCamera(Context context, final int type, final BaseListener listener) {
        if(cameraDialogUtil==null){
            cameraDialogUtil=new CameraDialogUtil(context);
        }
        cameraDialogUtil.showDialog(new CameraListener() {//type
            @Override
            public void takePic() {
                if(type==0){
                    listener.successful(0);
                }else if(type==1){
                    listener.successful(1);
                }else if(type==2){
                    listener.successful(2);
                }else if(type==3){
                    listener.successful(3);
                }else if(type==4){
                    listener.successful(4);
                }else if(type==5){
                    listener.successful(5);
                }else if(type==6){
                    listener.successful(6);
                }
            }
            @Override
            public void chooseLocal() {
                if(type==0){
                    listener.successful(7);
                }else if(type==1){
                    listener.successful(8);
                }else if(type==2){
                    listener.successful(9);
                }else if(type==3){
                    listener.successful(10);
                }else if(type==4){
                    listener.successful(11);
                }else if(type==5){
                    listener.successful(12);
                }else if(type==6){
                    listener.successful(13);
                }
            }
            @Override
            public void cancel() {
                cameraDialogUtil.dialog.dismiss();
                listener.successful(100);
            }
        });

    }

    @Override
    public void dissCamera() {
        if(cameraDialogUtil!=null){
            cameraDialogUtil.dialog.dismiss();
        }
    }

    @Override
    public void getIdentificationInfo(final NomalTaxesCarriverView view,
                                      final CarriverIdentificationListener listener) {

        view.showLoading();
        mineApi.getCarrivIerdentidiceInfo().enqueue(new CallBack<CarrierIndenditicationDto>() {
            @Override
            public void success(CarrierIndenditicationDto response) {
                view.dissLoading();
                listener.driviceIndetification(response);
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

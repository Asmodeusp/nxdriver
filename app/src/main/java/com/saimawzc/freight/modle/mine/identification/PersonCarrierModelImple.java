package com.saimawzc.freight.modle.mine.identification;

import android.content.Context;
import android.util.Log;

import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.base.CameraListener;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.login.AreaDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.dto.my.identification.CarrierIndenditicationDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.mine.identificaion.PersonCarrierView;
import com.saimawzc.freight.weight.utils.CameraDialogUtil;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.identification.CarriverIdentificationListener;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/3.
 * 个人承运商认证
 */

public class PersonCarrierModelImple extends BasEModeImple implements PersonCarrierModel {
    private CameraDialogUtil cameraDialogUtil;

    @Override
    public void identification(final PersonCarrierView view, final BaseListener listener) {
        JSONObject jsonObject=new JSONObject();
        view.showLoading();
        UserInfoDto loginDo= Hawk.get(PreferenceKey.USER_INFO);
        if(loginDo==null){
            listener.onFail("用户信息为空");
            return;
        }
        try {
            jsonObject.put("carrierType",1);//承运商类型（1.个人承运商 2.一般纳税人 3.小规模企业）
            jsonObject.put("userName",view.getUser());
            jsonObject.put("systemIdent","1");//安卓获取IOS，安卓1
            jsonObject.put("address",view.getArea());
            jsonObject.put("frontIdCard",view.sringImgPositive());//身份证正面
            jsonObject.put("reverseIdCard",view.sringImgOtherSide());//身份证反面
            jsonObject.put("idCardNum",view.getIdNum());//身份证号码
            jsonObject.put("userAccount",loginDo.getUserAccount());
            jsonObject.put("userCode",loginDo.getUserCode());
            jsonObject.put("userId",loginDo.getId());
            jsonObject.put("userSource","Android");
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

    @Override //重新认证
    public void reidentification(final PersonCarrierView view, final BaseListener listener) {
        JSONObject jsonObject=new JSONObject();
        view.showLoading();
        UserInfoDto loginDo= Hawk.get(PreferenceKey.USER_INFO);
        if(loginDo==null){
            listener.onFail("用户信息为空");
            return;
        }
        try {
            jsonObject.put("carrierType",1);//承运商类型（1.个人承运商 2.一般纳税人 3.小规模企业）
            jsonObject.put("userName",view.getUser());
            jsonObject.put("systemIdent","1");//安卓获取IOS，安卓1
            jsonObject.put("address",view.getArea());
            jsonObject.put("frontIdCard",view.sringImgPositive());//身份证正面
            jsonObject.put("reverseIdCard",view.sringImgOtherSide());//身份证反面
            jsonObject.put("idCardNum",view.getIdNum());//身份证号码
            jsonObject.put("userAccount",loginDo.getUserAccount());
            jsonObject.put("userCode",loginDo.getUserCode());
            jsonObject.put("userId",loginDo.getId());
            jsonObject.put("userSource","Android");
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

    @Override
    public void showCamera(Context context, final int type, final BaseListener listener) {
        if(cameraDialogUtil==null){
            cameraDialogUtil=new CameraDialogUtil(context);
        }

        cameraDialogUtil.showDialog(new CameraListener() {//type
            @Override
            public void takePic() {
                if(type==0){
                    listener.successful(0);//0身份证正面拍照
                }else if(type==1){
                    listener.successful(1);//身份证反面拍照
                }
            }
            @Override
            public void chooseLocal() {
                if(type==0){
                    listener.successful(2);//身份正面证本地选择
                }else if(type==1) {
                    listener.successful(3);//身份证反面本地选择
                }
            }
            @Override
            public void cancel() {
                cameraDialogUtil.dialog.dismiss();
            }
        });

    }
    @Override
    public void dissCamera() {
        if(cameraDialogUtil!=null){
            cameraDialogUtil.dialog.dismiss();
        }
    }

     /**
      * 获取区域信息
      * ***/
    @Override
    public void getArea(BaseListener listener) {

        JSONObject jsonObject=new JSONObject();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        mineApi.getArea().enqueue(new CallBack<List<AreaDto>>() {
            @Override
            public void success(List<AreaDto> response) {
                Hawk.put(PreferenceKey.CITY_INFO,response);
            }

            @Override
            public void fail(String code, String message) {

            }
        });
    }

    @Override
    public void getIdentificationInfo(final PersonCarrierView view,
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

package com.saimawzc.freight.modle.mine.identification;

import android.content.Context;
import android.util.Log;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.base.CameraListener;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.dto.my.identification.DriviceerIdentificationDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.mine.identificaion.DriveLicesenCarrierView;
import com.saimawzc.freight.weight.utils.CameraDialogUtil;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.identification.DriviceIdentificationListener;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/4.
 */

public class DriveLicenseModelImple extends BasEModeImple
        implements DriveLicenseModel {
    private CameraDialogUtil cameraDialogUtil;

    @Override
    public void identification(final DriveLicesenCarrierView view, final BaseListener listener,int isConsistent) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        UserInfoDto loginDo= Hawk.get(PreferenceKey.USER_INFO);
        if(loginDo==null){
            listener.onFail("用户信息为空");
            return;
        }
        try {
            jsonObject.put("accountType","3");
            jsonObject.put("address",view.getArea());
            jsonObject.put("avatar","");//身份证反面
            jsonObject.put("driverImg",view.sringDriverLincense());
            jsonObject.put("frontIdCard",view.sringImgPositive());
            jsonObject.put("reverseIdCard",view.sringImgOtherSide());
            jsonObject.put("idCardNum",view.getIdNum());
            jsonObject.put("lastVisitTime", BaseActivity.getCurrentTime("yyyy-MM-dd HH:mm"));
            jsonObject.put("lastVisitTime", "");
            jsonObject.put("roleName","司机");
            jsonObject.put("userAccount",loginDo.getUserAccount());
            jsonObject.put("userCode",loginDo.getUserCode());
            jsonObject.put("userId",loginDo.getId());
            jsonObject.put("userName",view.getUser());
            jsonObject.put("province",view.proviceName());
            jsonObject.put("provinceId",view.proviceId());
            jsonObject.put("city",view.cityName());
            jsonObject.put("cityId",view.cityId());
            jsonObject.put("area",view.countryName());
            jsonObject.put("areaId",view.countryId());
            jsonObject.put("isConsistent",isConsistent);
            jsonObject.put("driverType",view.driverType());
            jsonObject.put("driverName",view.driverName());
            jsonObject.put("driverIdCard",view.driverIdCard());
            jsonObject.put("driverOneTime",view.driverOneTime());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.sjInentification(body).enqueue(new CallBack<EmptyDto>() {
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
    public void reidentification(final DriveLicesenCarrierView view, final BaseListener listener,int isConsistent) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        UserInfoDto loginDo= Hawk.get(PreferenceKey.USER_INFO);
        if(loginDo==null){
            listener.onFail("用户信息为空");
            return;
        }
        try {
            jsonObject.put("accountType","3");
            jsonObject.put("address",view.getArea());
            jsonObject.put("avatar","");//身份证反面
            jsonObject.put("driverImg",view.sringDriverLincense());
            jsonObject.put("frontIdCard",view.sringImgPositive());
            jsonObject.put("reverseIdCard",view.sringImgOtherSide());
            jsonObject.put("idCardNum",view.getIdNum());
            jsonObject.put("lastVisitTime", BaseActivity.getCurrentTime("yyyy-MM-dd HH:mm"));
            jsonObject.put("lastVisitTime", "");
            jsonObject.put("roleName","司机");
            jsonObject.put("userAccount",loginDo.getUserAccount());
            jsonObject.put("userCode",loginDo.getUserCode());
            jsonObject.put("userId",loginDo.getId());
            jsonObject.put("userName",view.getUser());
            jsonObject.put("province",view.proviceName());
            jsonObject.put("provinceId",view.proviceId());
            jsonObject.put("city",view.cityName());
            jsonObject.put("cityId",view.cityId());
            jsonObject.put("area",view.countryName());
            jsonObject.put("areaId",view.countryId());
            jsonObject.put("isConsistent",isConsistent);
            jsonObject.put("driverName",view.driverName());
            jsonObject.put("driverIdCard",view.driverIdCard());
            jsonObject.put("driverOneTime",view.driverOneTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.resjInentification(body).enqueue(new CallBack<EmptyDto>() {
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
                }else if(type==2){
                    listener.successful(2);//行驶证
                }
            }
            @Override
            public void chooseLocal() {
                if(type==0){
                    listener.successful(3);//身份正面证本地选择
                }else if(type==1) {
                    listener.successful(4);//身份证反面本地选择
                } else if(type==2){
                    listener.successful(5);//行驶证
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

    /***
     * 获取详情认证
     * **/
    @Override
    public void getIdentificationInfo(final DriveLicesenCarrierView view, final DriviceIdentificationListener listener) {
        view.showLoading();
        mineApi.getDriviceIdentidiceInfo().enqueue(new CallBack<DriviceerIdentificationDto>() {
            @Override
            public void success(DriviceerIdentificationDto response) {
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

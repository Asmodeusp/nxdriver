package com.saimawzc.freight.modle.mine.car;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.base.CameraListener;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.dto.my.CarBrandDto;
import com.saimawzc.freight.dto.my.CarTypeDo;
import com.saimawzc.freight.dto.my.car.CarIsRegsister;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.dto.my.car.TrafficDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.mine.car.ResisterCarView;
import com.saimawzc.freight.weight.utils.CameraDialogUtil;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.car.CarBrandListener;
import com.saimawzc.freight.weight.utils.listen.car.CarTypeListener;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/6.
 */

public class ResisterCarModelImple extends BasEModeImple
        implements ResisterCarModel{

    private CameraDialogUtil cameraDialogUtil;
    @Override
    public void identification(final ResisterCarView view, final BaseListener listener,int isConsistent) {

        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        UserInfoDto loginDo= Hawk.get(PreferenceKey.USER_INFO);
        if(loginDo==null){
            listener.onFail("用户信息为空");
            return;
        }
        try {
            jsonObject.put("isConsistent",isConsistent);
            jsonObject.put("carColor",view.getCarColor());
            jsonObject.put("carHeigth",view.carHeight());
            jsonObject.put("carLength",view.carLength());//
            jsonObject.put("carWigth",view.carwith());
            jsonObject.put("carLoad",view.getCarWeight());
            jsonObject.put("carName",view.getUser());
            jsonObject.put("carNo",view.getCarNum());
            jsonObject.put("ifQualification", view.getAllowQuali());
            jsonObject.put("vehicleSurvey", view.stringCarInfo());
            jsonObject.put("operationLicense",view.stringTransport());
            jsonObject.put("vehicleLicense",view.stringDrivinglicense());

            jsonObject.put("tranNo",view.getTransportNum());

            jsonObject.put("allowAdd",view.getIsAllowAdd());//是否允许被添加

            jsonObject.put("registerLocation",view.location());
            jsonObject.put("carTypeId",view.getCarTypeId());
            jsonObject.put("carTypeName",view.getCarModel());
            jsonObject.put("carBrandId",view.getCarBrandId());//品牌ID
            jsonObject.put("carBrandName",view.getCarBrand());
            jsonObject.put("invitEnter",view.getInvitEnter());

            jsonObject.put("tranCarNo",view.gettranCarNo());
            jsonObject.put("tranCarName",view.gettranCarName());
            jsonObject.put("pictureTranNo",view.getpictureTranNo());

            jsonObject.put("boardHeight",view.boardHeight());
            jsonObject.put("licenseRegTime",view.licenseRegTime());
            jsonObject.put("isDangerCarType",view.isDangerous());
            jsonObject.put("rib1",view.rib1());
            jsonObject.put("rib2",view.rib2());
            jsonObject.put("rib3",view.rib3());
            jsonObject.put("rib4",view.rib4());
            jsonObject.put("rib5",view.rib5());
            if(view.tfaffic()!=null){
                jsonObject.put("vin",view.tfaffic().getVin());
                jsonObject.put("cmpNm",view.tfaffic().getCmpNm());
                jsonObject.put("vclWnrPhn",view.tfaffic().getVclWnrPhn());
                jsonObject.put("vehOwnName",view.tfaffic().getVehOwnName());
                jsonObject.put("areaName",view.tfaffic().getAreaName());
                jsonObject.put("vclTpNm",view.tfaffic().getVclTpNm());
                jsonObject.put("vbrndCdNm",view.tfaffic().getVbrndCdNm());
                jsonObject.put("prdCdNm",view.tfaffic().getPrdCdNm());
                jsonObject.put("vclTn",view.tfaffic().getVclTn());
                jsonObject.put("ldTn",view.tfaffic().getLdTn());
                jsonObject.put("vclDrwTn",view.tfaffic().getVclDrwTn());
                jsonObject.put("boxLng",view.tfaffic().getBoxLng());
                jsonObject.put("boxWdt",view.tfaffic().getBoxWdt());
                jsonObject.put("boxHgt",view.tfaffic().getBoxHgt());
                jsonObject.put("vclAxs",view.tfaffic().getVclAxs());
                jsonObject.put("serviceName",view.tfaffic().getServiceName());
                jsonObject.put("servicePhone",view.tfaffic().getServicePhone());

                jsonObject.put("vclLng",view.tfaffic().getVclLng());
                jsonObject.put("vclWdt",view.tfaffic().getVclWdt());
                jsonObject.put("vclHgt",view.tfaffic().getVclHgt());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.addCar(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                listener.successful();
                try{
                    Hawk.put(PreferenceKey.ISCAR_ACHE,null);
                }catch (Exception e){

                }

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
                    listener.successful(0);//道路运输证
                }else if(type==1){
                    listener.successful(1);//行驶证
                }else if(type==2){
                    listener.successful(2);//车辆信息
                }
            }
            @Override
            public void chooseLocal() {
                if(type==0){
                    listener.successful(3);//道路运输证
                }else if(type==1) {
                    listener.successful(4);//行驶证
                } else if(type==2){
                    listener.successful(5);//车辆信息
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

    //获取车辆类型
    @Override
    public void getCarType(final ResisterCarView view, final CarTypeListener listener) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
       mineApi.getCarType(body).enqueue(new CallBack<List<CarTypeDo>>() {
           @Override
           public void success(List<CarTypeDo> response) {
               view.dissLoading();
               listener.callbacktype(response);
           }

           @Override
           public void fail(String code, String message) {
             view.dissLoading();
               view.Toast(message);
           }
       });


    }
    //获取车辆品牌
    @Override
    public void getBrand(final ResisterCarView view,final CarBrandListener listener,String carType) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("carTypeName",carType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getCarBrand(body).enqueue(new CallBack<List<CarBrandDto>>() {
            @Override
            public void success(List<CarBrandDto> response) {
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
       /***
        * 获取车辆详情数据
        * ***/
    @Override
    public void getCarInfo(final ResisterCarView view, final CarTypeListener listener, String id) {

        if(TextUtils.isEmpty(id)){
            SearchCarDto carDto=Hawk.get(PreferenceKey.ISCAR_ACHE);
            listener.carinfoListen(carDto);
        }else {
            view.showLoading();
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("id",id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON,jsonObject.toString());
            mineApi.getCarInfo(body).enqueue(new CallBack<SearchCarDto>() {
                @Override
                public void success(SearchCarDto response) {
                    view.dissLoading();
                    listener.carinfoListen(response);
                }

                @Override
                public void fail(String code, String message) {
                    view.dissLoading();
                    view.Toast(message);
                }
            });
        }

    }

    @Override
    public void isregster(final ResisterCarView view, final CarTypeListener listener, String carNum) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("carNo",carNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.isChange(body).enqueue(new CallBack<CarIsRegsister>() {
            @Override
            public void success(CarIsRegsister response) {
                view.dissLoading();
                listener.isRegsister(response);
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }


    /***
     * 修改车辆
     * **/
    @Override
    public void modifyCar(final ResisterCarView view,final BaseListener listener,String id,int isConsistent ) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        UserInfoDto loginDo= Hawk.get(PreferenceKey.USER_INFO);
        if(loginDo==null){
            listener.onFail("用户信息为空");
            return;
        }
        try {

            jsonObject.put("id",id);
            jsonObject.put("isConsistent",isConsistent);
            jsonObject.put("carColor",view.getCarColor());
            jsonObject.put("carHeigth",view.carHeight());
            jsonObject.put("carLength",view.carLength());//
            jsonObject.put("carWigth",view.carwith());
            jsonObject.put("carLoad",view.getCarWeight());
            jsonObject.put("carName",view.getUser());
            jsonObject.put("carNo",view.getCarNum());
            jsonObject.put("ifQualification", view.getAllowQuali());
            jsonObject.put("vehicleSurvey", view.stringCarInfo());
            jsonObject.put("operationLicense",view.stringTransport());
            jsonObject.put("vehicleLicense",view.stringDrivinglicense());
            jsonObject.put("registerLocation",view.location());
            jsonObject.put("tranNo",view.getTransportNum());

            jsonObject.put("allowAdd",view.getIsAllowAdd());//是否允许被添加


            jsonObject.put("carTypeId",view.getCarTypeId());
            jsonObject.put("carTypeName",view.getCarModel());
            jsonObject.put("carBrandId",view.getCarBrandId());//品牌ID
            jsonObject.put("carBrandName",view.getCarBrand());
            jsonObject.put("invitEnter",view.getInvitEnter());

            jsonObject.put("tranCarNo",view.gettranCarNo());
            jsonObject.put("tranCarName",view.gettranCarName());
            jsonObject.put("pictureTranNo",view.getpictureTranNo());

            jsonObject.put("boardHeight",view.boardHeight());
            jsonObject.put("licenseRegTime",view.licenseRegTime());
            jsonObject.put("isDangerCarType",view.isDangerous());
            jsonObject.put("rib1",view.rib1());
            jsonObject.put("rib2",view.rib2());
            jsonObject.put("rib3",view.rib3());
            jsonObject.put("rib4",view.rib4());
            jsonObject.put("rib5",view.rib5());

            if(view.tfaffic()!=null){
                jsonObject.put("vin",view.tfaffic().getVin());
                jsonObject.put("cmpNm",view.tfaffic().getCmpNm());
                jsonObject.put("vclWnrPhn",view.tfaffic().getVclWnrPhn());
                jsonObject.put("vehOwnName",view.tfaffic().getVehOwnName());
                jsonObject.put("areaName",view.tfaffic().getAreaName());
                jsonObject.put("vclTpNm",view.tfaffic().getVclTpNm());
                jsonObject.put("vbrndCdNm",view.tfaffic().getVbrndCdNm());
                jsonObject.put("prdCdNm",view.tfaffic().getPrdCdNm());
                jsonObject.put("vclTn",view.tfaffic().getVclTn());
                jsonObject.put("ldTn",view.tfaffic().getLdTn());
                jsonObject.put("vclDrwTn",view.tfaffic().getVclDrwTn());
                jsonObject.put("boxLng",view.tfaffic().getBoxLng());
                jsonObject.put("boxWdt",view.tfaffic().getBoxWdt());
                jsonObject.put("boxHgt",view.tfaffic().getBoxHgt());
                jsonObject.put("vclAxs",view.tfaffic().getVclAxs());
                jsonObject.put("serviceName",view.tfaffic().getServiceName());
                jsonObject.put("servicePhone",view.tfaffic().getServicePhone());

                jsonObject.put("vclLng",view.tfaffic().getVclLng());
                jsonObject.put("vclWdt",view.tfaffic().getVclWdt());
                jsonObject.put("vclHgt",view.tfaffic().getVclHgt());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.modifyCar(body).enqueue(new CallBack<EmptyDto>() {
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
    public void ZhiYunCarInfo(final ResisterCarView view, String carNo, int colorType) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("carNo",carNo);
            jsonObject.put("carColor",colorType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getTraffic(body).enqueue(new CallBack<TrafficDto>() {
            @Override
            public void success(TrafficDto response) {
                view.getTrafficDto(response);

            }

            @Override
            public void fail(String code, String message) {
            }
        });
    }
}

package com.saimawzc.freight.modle.order.modelImple.taxi.servive;

import android.content.Context;
import android.util.Log;

import com.saimawzc.freight.base.CameraListener;
import com.saimawzc.freight.dto.face.EditTaxaDto;
import com.saimawzc.freight.dto.my.CarBrandDto;
import com.saimawzc.freight.dto.my.CarTypeDo;
import com.saimawzc.freight.dto.taxi.service.CarInfo;
import com.saimawzc.freight.dto.taxi.service.ServiceInfo;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.taxi.service.ServiceSubmitModel;
import com.saimawzc.freight.view.order.taxi.service.ServiceSubmitView;
import com.saimawzc.freight.weight.utils.CameraDialogUtil;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.http.JsonUtil;
import com.saimawzc.freight.weight.utils.http.RequestHeaderInterceptor;
import com.saimawzc.freight.weight.utils.http.RequestLogInterceptor;
import com.saimawzc.freight.weight.utils.listen.car.CarBrandListener;
import com.saimawzc.freight.weight.utils.listen.car.CarTypeListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.saimawzc.freight.constants.Constants.baseSwUrl;



/**
 * Created by Administrator on 2020/7/31.
 * 忘记密码
 */

public class ServiceSubmitModelImple extends BasEModeImple implements ServiceSubmitModel {


    @Override
    public void edit(final ServiceSubmitView view) {
        view.showLoading();

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("carId",view.carId());
            jsonObject.put("clsyr",view.clsyr());
            jsonObject.put("cycc",view.cycc());
            jsonObject.put("cycph",view.cycph());
            jsonObject.put("cycsfgk",view.cycsfgk());
            jsonObject.put("cycsjsyr",view.cycsjsyr());
            jsonObject.put("cycsyrlxfs",view.cycsyrlxfs());
            jsonObject.put("cycx",view.cycx());
            jsonObject.put("cycxszfjBos",view.cycxszfjBos());
            jsonObject.put("cyczpfjBos",view.cyczpfjBos());
            jsonObject.put("cypp",view.cypp());
            jsonObject.put("cyzz",view.cyzz());
            jsonObject.put("czsmgkfjBos",view.czsmgkfjBos());
            jsonObject.put("fwfsfzjhm",view.fwfsfzjhm());
            jsonObject.put("fwfsfzjlx",view.fwfsfzjlx());
            jsonObject.put("fwfuuid",view.fwfuuid());
            jsonObject.put("fwfxm",view.fwfxm());
            jsonObject.put("jszfjBos",view.jszfjBos());
            jsonObject.put("jszjhm",view.jszjhm());
            jsonObject.put("khrsfzjhm",view.khrsfzjhm());
            jsonObject.put("khrxm",view.khrxm());
            jsonObject.put("khyh",view.khyh());
            jsonObject.put("ptzcsj",view.ptzcsj());
            jsonObject.put("qysmgkfjBos",view.qysmgkfjBos());
            jsonObject.put("role",view.role());
            jsonObject.put("roleId",view.roleId());
            jsonObject.put("skzh",view.skzh());
            jsonObject.put("smrzfjBos",view.smrzfjBos());
            jsonObject.put("ssdq",view.ssdq());
            jsonObject.put("xszjhm",view.xszjhm());
            jsonObject.put("yddh",view.yddh());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        String url=baseSwUrl+"api/modifiyTaxationBaseCz";
        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new RequestLogInterceptor())
                .addInterceptor(new RequestHeaderInterceptor())
                .hostnameVerifier(new Http.TrustAllHostnameVerifier())//信任证书
                .retryOnConnectionFailure(true)//重连
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                view.dissLoading();
                view.Toast(e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String content=response.body().string();
                view.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.dissLoading();
                        if(response.isSuccessful()){
                            try {
                                EditTaxaDto editTaxaDto= JsonUtil.fromJson(content, EditTaxaDto.class);
                                view.getData(editTaxaDto);

                            }catch (Exception E){
                                view.Toast("Json转换失败");
                            }
                        }else {
                            view.Toast("请求数据失败");
                        }
                    }
                });

            }
        });
    }
    private CameraDialogUtil cameraDialogUtil;
    @Override
    public void showCamera(final ServiceSubmitView view, Context context, final int type) {
        if(cameraDialogUtil==null){
            cameraDialogUtil=new CameraDialogUtil(context);
        }
        cameraDialogUtil.showDialog(new CameraListener() {//type
            @Override
            public void takePic() {

                if(type==0){
                    view.OnDealCamera(0);
                }else if(type==1){
                    view.OnDealCamera(1);
                }else if(type==2){
                    view.OnDealCamera(2);
                }else if(type==3){
                    view.OnDealCamera(3);
                }else if(type==4){
                    view.OnDealCamera(4);
                }else if(type==5){
                    view.OnDealCamera(5);
                }
                cameraDialogUtil.dialog.dismiss();

            }
            @Override
            public void chooseLocal() {
                if(type==0){
                    view.OnDealCamera(6);
                }else if(type==1){
                    view.OnDealCamera(7);
                }else if(type==2){
                    view.OnDealCamera(8);
                }else if(type==3){
                    view.OnDealCamera(9);
                }else if(type==4){
                    view.OnDealCamera(10);
                }else if(type==5){
                    view.OnDealCamera(11);
                }
                cameraDialogUtil.dialog.dismiss();

            }
            @Override
            public void cancel() {
                cameraDialogUtil.dialog.dismiss();
            }
        });
    }

    @Override
    public void getCarType(final ServiceSubmitView view, final CarTypeListener listener) {
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

    @Override
    public void getBrand(final ServiceSubmitView view, final CarBrandListener listener, String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("carTypeName",type);
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

    @Override
    public void getSfInfo(final ServiceSubmitView view, String roleId) {
        view.showLoading();

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("roleId",roleId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        String url=baseSwUrl+"api/queryWzcUserInfo";
        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new RequestLogInterceptor())
                .addInterceptor(new RequestHeaderInterceptor())
                .hostnameVerifier(new Http.TrustAllHostnameVerifier())//信任证书
                .retryOnConnectionFailure(true)//重连
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                view.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.dissLoading();
                        view.Toast(e.getMessage());
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                view.dissLoading();
                Log.e("msg",response.toString());
                view.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.isSuccessful()){
                            try {
                                String content=response.body().string();
                                ServiceInfo serviceInfo= JsonUtil.fromJson(content, ServiceInfo.class);
                                view.getSfInfo(serviceInfo);
                            }catch (Exception E){
                            }

                        }else {
                            view.Toast("请求数据失败");
                        }
                    }
                });

            }
        });
    }

    @Override
    public void getCarInfo(final ServiceSubmitView view, String carNO) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("carNo",carNO);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getCarInfoByCarNo(body).enqueue(new CallBack<CarInfo>() {
            @Override
            public void success(CarInfo response) {
                view.dissLoading();
                view.getCarInfo(response);
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

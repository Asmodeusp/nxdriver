package com.saimawzc.freight.modle.order.modelImple.taxi;


import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.saimawzc.freight.dto.face.EditTaxaDto;
import com.saimawzc.freight.dto.login.TaxiAreaDto;
import com.saimawzc.freight.dto.taxi.TJSuccessfulDto;
import com.saimawzc.freight.dto.taxi.TjSWDto;
import com.saimawzc.freight.dto.taxi.TjSubmitDto;
import com.saimawzc.freight.dto.taxi.TjxqDato;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.taxi.PublisherModel;
import com.saimawzc.freight.view.order.taxi.PublisherView;
import com.saimawzc.freight.view.order.taxi.TaxiAdressView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.http.JsonUtil;
import com.saimawzc.freight.weight.utils.http.RequestHeaderInterceptor;
import com.saimawzc.freight.weight.utils.http.RequestLogInterceptor;

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


public class PublisherModelImple extends BasEModeImple implements PublisherModel {


    @Override
    public void getTaxiPush(final PublisherView view, String roleId) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("roleId",roleId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        String url=baseSwUrl+"api/queryTaxationBaseHzOne";
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
            public void onResponse(Call call, Response response) throws IOException {
                view.dissLoading();
                Log.e("msg",response.toString());
                if(response.isSuccessful()){
                    String content=response.body().string();
                    try {


                       final TjxqDato editTaxaDto= JsonUtil.fromJson(content, TjxqDato.class);
                        if(editTaxaDto!=null){

                            view.getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(!editTaxaDto.isSuccess()){
                                        view.Toast(editTaxaDto.getMessage());
                                        return;

                                    }
                                    view.getTaxiDto(editTaxaDto.getData());
                                }
                            });


                        }

                    }catch (Exception E){

                    }

                }else {
                    view.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.Toast("请求数据失败");
                        }
                    });

                }
            }
        });
    }

    @Override
    public void submit(final PublisherView view, TjSubmitDto dto) {
        view.showLoading();

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("fddbr",dto.getMainPerson());
            jsonObject.put("gsdz",dto.getAdressDelation());
            jsonObject.put("jyfw",dto.getJyfw());
            jsonObject.put("nsrlx",dto.getType());
            jsonObject.put("nsrmc",dto.getCompanyName());
            jsonObject.put("nsrsbh",dto.getNsrSbNum());
            jsonObject.put("ptzcsj",dto.getZctime());
            jsonObject.put("qyyddh",dto.getPhone());
            jsonObject.put("role",dto.getRole());
            jsonObject.put("roleId",dto.getRoleId());
            jsonObject.put("slrq",dto.getTvclData());
            jsonObject.put("ssdq",dto.getCityId());
            jsonObject.put("xxdz",dto.getAdressDelation());
            jsonObject.put("yyzzfjBos",dto.getBussnessImg());
            jsonObject.put("zczb",dto.getZcmoney());
            jsonObject.put("zgswjg",dto.getSwjg());
            if(!TextUtils.isEmpty(dto.getId())){
                jsonObject.put("id",dto.getId());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        String url=baseSwUrl+"api/modifiyTaxationBaseHz";
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
            public void onResponse(Call call, Response response) throws IOException {
                view.dissLoading();
                Log.e("msg",response.toString());
                if(response.isSuccessful()){
                    String content=response.body().string();
                    try {
                       // TJSuccessfulDto editTaxaDto= JsonUtil.fromJson(content, TJSuccessfulDto.class);
                        view.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.oncomplete();
                            }
                        });

                    }catch (Exception E){

                    }

                }else {
                    view.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.Toast("请求数据失败");
                        }
                    });

                }
            }
        });
    }

}

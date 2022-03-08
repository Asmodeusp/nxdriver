package com.saimawzc.freight.modle.order.modelImple.taxi.servive;


import android.text.TextUtils;
import android.util.Log;

import com.saimawzc.freight.dto.face.FaceQueryDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.dto.taxi.service.ServiceData;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.taxi.service.MyServiceListModel;
import com.saimawzc.freight.view.order.taxi.service.MySeriviceListView;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.http.JsonUtil;
import com.saimawzc.freight.weight.utils.http.RequestHeaderInterceptor;
import com.saimawzc.freight.weight.utils.http.RequestLogInterceptor;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.saimawzc.freight.constants.Constants.baseSwUrl;


public class MyServiceListModelImple extends BasEModeImple implements MyServiceListModel {




    @Override
    public void getList(final MySeriviceListView view, String idcardNum,String roleId) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("fwfsfzjhm",idcardNum);
             jsonObject.put("roleId",roleId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        String url=baseSwUrl+"api/queryTaxationBaseCzMore";
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
                        final ServiceData editTaxaDto= JsonUtil.fromJson(content, ServiceData.class);

                        view.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(editTaxaDto!=null){
                                    if(!editTaxaDto.isSuccess()){
                                        view.Toast(editTaxaDto.getMessage());
                                        return;
                                    }
                                    view.getList(editTaxaDto.getData());
                                }
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

    @Override
    public void queryFaceDto(final MySeriviceListView view, String carNo,String sfznum, UserInfoDto userInfoDto) {
        view.showLoading();

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("fwfsfzjhm",sfznum);
            if(userInfoDto!=null&&!TextUtils.isEmpty(userInfoDto.getRoleId())){
                jsonObject.put("roleId",userInfoDto.getRoleId());
            }
            jsonObject.put("cycph",carNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        String url=baseSwUrl+"api/queryTaxationBaseCzOne";
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


                view.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.isSuccessful()){

                            try {
                                String content=response.body().string();
                                FaceQueryDto faceQueryDto= JsonUtil.fromJson(content, FaceQueryDto.class);
                                view.getFaceDto(faceQueryDto);
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
}

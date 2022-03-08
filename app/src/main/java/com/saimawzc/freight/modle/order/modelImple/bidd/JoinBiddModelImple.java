package com.saimawzc.freight.modle.order.modelImple.bidd;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.face.FaceQueryDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.dto.order.bidd.JoinBiddDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.bidd.JoinBiddModel;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.ui.order.face.FaceLivenessExpActivity;
import com.saimawzc.freight.view.order.JoinBiddView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.http.JsonResult;
import com.saimawzc.freight.weight.utils.http.JsonUtil;
import com.saimawzc.freight.weight.utils.http.RequestHeaderInterceptor;
import com.saimawzc.freight.weight.utils.http.RequestLogInterceptor;
import com.saimawzc.freight.weight.utils.listen.order.bidd.JoinBiddListener;
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


public class JoinBiddModelImple extends BasEModeImple implements JoinBiddModel {

    @Override
    public void getBiddDelation(final JoinBiddView view, final JoinBiddListener listener, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getBiddDelation(body).enqueue(new CallBack<JoinBiddDto>() {
            @Override
            public void success(JoinBiddDto response) {
                view.dissLoading();
                listener.getBiddDelation(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void addBibb(final JoinBiddView view, final JoinBiddListener listener, String id, String price,
                        String weight, String type,String carNum,String sijiId,String carId) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("biddPrice",price);
            jsonObject.put("biddWeight",weight);
            jsonObject.put("wayBillType",type);
            jsonObject.put("carNum",carNum);
            jsonObject.put("sjId",sijiId);
            jsonObject.put("carId",carId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.csybidd(body).enqueue(new CallBack<EmptyDto>() {
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

    @Override
    public void getsjBiddDelation(final JoinBiddView view, final JoinBiddListener listener, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getsjBiddDelation(body).enqueue(new CallBack<JoinBiddDto>() {
            @Override
            public void success(JoinBiddDto response) {
                view.dissLoading();
                listener.getBiddDelation(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void addsjBibb(final JoinBiddView view, final JoinBiddListener listener, String id,
                          String price, String weight, String type,String carNum,String carId) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("biddPrice",price);
            jsonObject.put("biddWeight",weight);
            jsonObject.put("wayBillType",type);
            jsonObject.put("carNum",carNum);
            jsonObject.put("carId",carId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.sjbidd(body).enqueue(new CallBack<EmptyDto>() {
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

    @Override
    public void queryFaceDto(final JoinBiddView view, String carId, UserInfoDto userInfoDto) {
        view.showLoading();

        JSONObject jsonObject=new JSONObject();
        try {
            if(userInfoDto!=null&&!TextUtils.isEmpty(userInfoDto.getRoleId())){
                jsonObject.put("role",userInfoDto.getRole());
                jsonObject.put("roleId",userInfoDto.getRoleId());
            }
            jsonObject.put("carId",carId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        String url=baseSwUrl+"api/queryTaxationBaseCz";
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
            public void onResponse(Call call, Response response) throws IOException {
                view.dissLoading();
                Log.e("msg",response.toString());
                if(response.isSuccessful()){
                      String content=response.body().string();
                      try {
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
}

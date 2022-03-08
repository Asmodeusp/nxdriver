package com.saimawzc.freight.modle.main;
import android.text.TextUtils;

import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.FrameDto;
import com.saimawzc.freight.dto.WaybillEncloDto;
import com.saimawzc.freight.dto.my.carrier.CarrierPageDto;
import com.saimawzc.freight.dto.my.lessess.LessessPageDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.DriverMainView;
import com.saimawzc.freight.view.mine.MineView;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
/**
 * Created by
 */
public class MainModelImple extends BasEModeImple implements MainModel{

    @Override
    public void getCarrierList(final DriverMainView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",1);
            jsonObject.put("pageSize",20);
            jsonObject.put("state",2);//state状态 （1.以添加 2.待确认）
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getMyCarrier(body).enqueue(new CallBack<CarrierPageDto>() {
            @Override
            public void success(CarrierPageDto response) {
              view.getMyCarrive(response.getList());
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void getLessessList(final DriverMainView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",1);
            jsonObject.put("pageSize",20);
            jsonObject.put("checkStatus",2);//state状态 （1.以添加 2.待确认）
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getLessessList(body).enqueue(new CallBack<LessessPageDto>() {
            @Override
            public void success(LessessPageDto response) {
                view.dissLoading();
                view.getmylessee(response.getList());
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void getDlilog(final DriverMainView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("",2);//state状态 （1.以添加 2.待确认）
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getFram(body).enqueue(new CallBack<FrameDto>() {
            @Override
            public void success(FrameDto response) {
                view.dissLoading();
                view.getDialog(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void getYdInfo(final DriverMainView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("","");//state状态 （1.以添加 2.待确认）
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getYdWeiLanInfo(body).enqueue(new CallBack<WaybillEncloDto>() {
            @Override
            public void success(WaybillEncloDto response) {
                view.dissLoading();
                view.getYdInfo(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                if(!TextUtils.isEmpty(message)){
                    view.Toast(message);
                    view.getYdInfo(null);
                }

            }
        });
    }

    @Override
    public void uploadGwWl(final DriverMainView view, String waybillId,
                           String highEnclosureId, String warnType, String location, final List<String> alreadyList) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("waybillId",waybillId);
            jsonObject.put("warnType",warnType);
            jsonObject.put("highEnclosureId",highEnclosureId);
            jsonObject.put("location",location);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.uplaodStayTime(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                view.Toast("您已进入高危围栏");
                Hawk.put(PreferenceKey.AlreadyUploadWl,alreadyList);


            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast("您已进入高危围栏，但是推送失败了");

            }
        });
    }


}

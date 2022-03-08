package com.saimawzc.freight.modle.mine;
import com.bumptech.glide.Glide;
import com.saimawzc.freight.R;
import com.saimawzc.freight.dto.my.PersonCenterDto;
import com.saimawzc.freight.dto.my.carrier.CarrierPageDto;
import com.saimawzc.freight.dto.my.lessess.LessessPageDto;
import com.saimawzc.freight.dto.wallet.SonAccountDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.mine.MineView;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.http.CallBackCode;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by
 */
public class MineModelImple extends BasEModeImple implements MineModel{


    @Override
    public void getPerson(final MineView view) {
        view.showLoading();
        mineApi.getPersoneCener().enqueue(new CallBack<PersonCenterDto>() {
            @Override
            public void success(PersonCenterDto response) {
                view.dissLoading();
               view.getPersonDto(response);
            }
            @Override
            public void fail(String code, String message) {
                view.Toast(message);
                view.dissLoading();
            }
        });
    }

    @Override
    public void getCarrierList(final MineView view) {
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
    public void getLessessList(final MineView view) {
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

    /***
     * 获取子账户信息
     * **/
    @Override
    public void getSonAccount(final MineView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.getSonAcountInfo(body).enqueue(new CallBackCode<SonAccountDto>() {
            @Override
            public void success(SonAccountDto response,String code) {
                view.dissLoading();
                view.getsonAccount(response,code);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.getsonAccount(null,code);
                view.Toast(message);
            }
        });
    }
}

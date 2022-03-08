package com.saimawzc.freight.modle.wallet.imple;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.travel.PresupTravelDto;
import com.saimawzc.freight.dto.wallet.BindBankDto;
import com.saimawzc.freight.dto.wallet.MsBankDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.wallet.BindBankModel;
import com.saimawzc.freight.view.wallet.BindBankView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class BindBankModelImple extends BasEModeImple implements BindBankModel {
    @Override
    public void bind(final BindBankView view, BindBankDto dto) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            if(dto!=null){
                jsonObject.put("clientType",dto.getClientType());
                jsonObject.put("clientName",dto.getClientName());
                jsonObject.put("idCode",dto.getIdCode());
                jsonObject.put("mobile",dto.getMobile());
                jsonObject.put("bankNo",dto.getBankNo());
                jsonObject.put("bankAcc",dto.getBankAcc());
                jsonObject.put("openBranch",dto.getOpenBranch());
                jsonObject.put("sex",dto.getSex());
                jsonObject.put("reprName",dto.getReprName());
                jsonObject.put("reprIdCode",dto.getReprIdCode());
                jsonObject.put("actorName",dto.getActorName());
                jsonObject.put("actorIdCode",dto.getActorIdCode());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.sign(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                view.oncomplete();
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void getBigBank(final BindBankView view, String str) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("queryBankName",str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.getBigBank(body).enqueue(new CallBack<List<MsBankDto>>() {
            @Override
            public void success(List<MsBankDto> response) {
                view.dissLoading();
                view.getBigBankList(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void cardBin(final BindBankView view, String carno) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("cardNo",carno);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.cardBin(body).enqueue(new CallBack<MsBankDto>() {
            @Override
            public void success(MsBankDto response) {
                view.dissLoading();
                view.getCarBin(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

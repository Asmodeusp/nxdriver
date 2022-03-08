package com.saimawzc.freight.modle.wallet.imple;
import com.saimawzc.freight.dto.wallet.MsBankDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.wallet.MsBankModel;
import com.saimawzc.freight.view.wallet.MsBankView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MsBankModelImple extends BasEModeImple implements MsBankModel {

    @Override
    public void getBankList(final MsBankView view, String bankName) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("bankName",bankName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.GETmSbANKlIST(body).enqueue(new CallBack<List<MsBankDto>>() {
            @Override
            public void success(List<MsBankDto> response) {
                view.dissLoading();
                view.getBinkList(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void getBigBankList(final MsBankView view, String queryBankName) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("queryBankName",queryBankName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.getBigBank(body).enqueue(new CallBack<List<MsBankDto>>() {
            @Override
            public void success(List<MsBankDto> response) {
                view.dissLoading();
                view.getBigBank(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

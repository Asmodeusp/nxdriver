package com.saimawzc.freight.modle.wallet.imple;

import android.os.Message;

import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.wallet.BindBankDto;
import com.saimawzc.freight.dto.wallet.MsBankDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.wallet.BKModel;
import com.saimawzc.freight.view.wallet.BKView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class BkModelImple extends BasEModeImple implements BKModel {
    public BkModelImple() {
        super();
    }

    public void bind(final BKView view, BindBankDto arg5) {
        view.showLoading();
        JSONObject jsonObject = new JSONObject();
        if(arg5 != null) {
            try {
                jsonObject.put("cardNo", arg5.getBankAcc());
                jsonObject.put("openBranch", arg5.getOpenBranch());
                jsonObject.put("mobile", arg5.getMobile());
                jsonObject.put("bankNo", arg5.getBankNo());
            }
            catch(JSONException v5) {
                v5.printStackTrace();
            }
        }

        this.bmsApi.bk(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString()))
                .enqueue(new CallBack<EmptyDto>() {
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

    public void cardBin(final BKView arg3, final String arg4) {
        arg3.showLoading();
        JSONObject v0 = new JSONObject();
        try {
            v0.put("cardNo", arg4);
        }
        catch(JSONException v4) {
            v4.printStackTrace();

        }
        bmsApi.cardBin(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), v0.toString()))
                .enqueue(new CallBack<MsBankDto>() {
                    @Override
                    public void success(MsBankDto response) {
                        arg3.dissLoading();
                        arg3.getCarBin(response);
                    }

                    @Override
                    public void fail(String code, String message) {
                        arg3.dissLoading();
                    }
                });

    }

    public void getBigBank(final BKView arg3, String arg4) {
        arg3.showLoading();
        JSONObject v0 = new JSONObject();
        try {
            v0.put("queryBankName", arg4);
        }
        catch(JSONException v4) {
            v4.printStackTrace();
        }

        this.bmsApi.getBigBank(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), v0.toString())).enqueue(new CallBack<List<MsBankDto>>() {
            @Override
            public void success(List<MsBankDto> response) {
                arg3.dissLoading();

            }

            @Override
            public void fail(String code, String message) {
                arg3.dissLoading();
                arg3.Toast(message);
            }
        });

    }
}

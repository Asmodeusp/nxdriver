package com.saimawzc.freight.modle.wallet.imple;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.wallet.SonAccountDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.wallet.SonAccountModel;
import com.saimawzc.freight.view.wallet.SignedWalletView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SonAccountModelImple extends BasEModeImple implements SonAccountModel {



    @Override
    public void getSonAccount(final SignedWalletView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.getSonAcountInfo(body).enqueue(new CallBack<SonAccountDto>() {
            @Override
            public void success(SonAccountDto response) {
                view.dissLoading();
                view.getSonAccoucnt(response);

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void setDefaultCrd(final SignedWalletView view, String carNo) {
        view.showLoading();
        JSONObject v0 = new JSONObject();
        try {
            v0.put("cardNo", carNo);
        }
        catch(JSONException v4) {
            v4.printStackTrace();
        }
        bmsApi.setDefaultCard(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), v0.toString())).enqueue(new CallBack<EmptyDto>() {
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
}

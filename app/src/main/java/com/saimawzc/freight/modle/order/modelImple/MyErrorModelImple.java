package com.saimawzc.freight.modle.order.modelImple;

import android.content.Context;
import com.saimawzc.freight.dto.order.error.MyErrDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.bidd.MyErrorModel;
import com.saimawzc.freight.view.order.error.MyErrorView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
public class MyErrorModelImple extends BasEModeImple implements MyErrorModel {

    @Override
    public void getErrorType(final MyErrorView view,String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getMyErrorList(body).enqueue(new CallBack<List<MyErrDto>>() {
            @Override
            public void success(List<MyErrDto> respon) {
                view.dissLoading();
                view.getErrorList(respon);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

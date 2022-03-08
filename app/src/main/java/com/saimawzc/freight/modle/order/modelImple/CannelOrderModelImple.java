package com.saimawzc.freight.modle.order.modelImple;

import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.bidd.CancelOrderModel;
import com.saimawzc.freight.view.order.error.CancelOrderView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
public class CannelOrderModelImple extends BasEModeImple implements CancelOrderModel {

    @Override
    public void submitError(final CancelOrderView view, String id, String reason) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("cancelOrderReason",reason);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.applyCancelOrder(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto respon) {
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

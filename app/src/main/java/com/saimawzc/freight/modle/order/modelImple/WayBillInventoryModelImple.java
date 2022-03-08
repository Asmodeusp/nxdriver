package com.saimawzc.freight.modle.order.modelImple;



import com.saimawzc.freight.dto.order.OrderInventoryDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.WayBillInventoryModel;
import com.saimawzc.freight.view.order.WayBillInventoryView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.order.WayBillInventoryListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 预运单清单
 * **/
public class WayBillInventoryModelImple extends BasEModeImple implements WayBillInventoryModel {
    @Override
    public void getWayBillList(final WayBillInventoryView view, final WayBillInventoryListener listListener, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getWayBillQd(body).enqueue(new CallBack<OrderInventoryDto>() {
            @Override
            public void success(final OrderInventoryDto response) {
                view.dissLoading();
                listListener.back(response.getList());

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

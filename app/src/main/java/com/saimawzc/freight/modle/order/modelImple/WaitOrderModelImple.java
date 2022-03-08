package com.saimawzc.freight.modle.order.modelImple;
import com.saimawzc.freight.dto.order.bill.MyPlanOrderDto;
import com.saimawzc.freight.dto.order.mainindex.WaitOrderDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.PlanOrderModel;
import com.saimawzc.freight.modle.order.modle.WaitOrderModel;
import com.saimawzc.freight.view.order.PlanOrderView;
import com.saimawzc.freight.view.order.WaitOrderView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.order.PlanOrderListener;
import com.saimawzc.freight.weight.utils.listen.order.WaitOrderListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class WaitOrderModelImple extends BasEModeImple implements WaitOrderModel {




    @Override
    public void getCarList(final WaitOrderView view, final WaitOrderListener listener, int page) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getWaitData(body).enqueue(new CallBack<WaitOrderDto>() {
            @Override
            public void success(WaitOrderDto response) {
                view.dissLoading();
                view.isLastPage(response.isLastPage());
                listener.waitordetList(response.getList());
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

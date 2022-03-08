package com.saimawzc.freight.modle.order.modelImple;
import android.text.TextUtils;

import com.saimawzc.freight.dto.order.PlanOrderReshDto;
import com.saimawzc.freight.dto.order.bill.MyPlanOrderDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.PlanOrderModel;
import com.saimawzc.freight.view.order.PlanOrderView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.order.PlanOrderListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PlanOrderModelImple extends BasEModeImple implements PlanOrderModel {


    @Override
    public void getCarList(final PlanOrderView view, final PlanOrderListener listener,
                           int page,String searchType,String searchValue,int waybillstatus) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            jsonObject.put("wayBillStatus",waybillstatus);
            if(!TextUtils.isEmpty(searchType)&&!TextUtils.isEmpty(searchValue)){
                if(!searchType.equals("全部")){
                    jsonObject.put(searchType,searchValue);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getMyPlanOrder(body).enqueue(new CallBack<MyPlanOrderDto>() {
            @Override
            public void success(MyPlanOrderDto response) {
                view.dissLoading();
                view.isLastPage(response.isLastPage());
                listener.planOrderList(response.getList());
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void getsjCarList(final PlanOrderView view, final PlanOrderListener listener, int page, String searchType, String searchValue) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            if(!TextUtils.isEmpty(searchType)&&!TextUtils.isEmpty(searchValue)){
                if(!searchType.equals("全部")){
                    jsonObject.put(searchType,searchValue);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getSjMyPlanOrder(body).enqueue(new CallBack<MyPlanOrderDto>() {
            @Override
            public void success(MyPlanOrderDto response) {
                view.dissLoading();
                view.isLastPage(response.isLastPage());
                listener.planOrderList(response.getList());
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void reshData(final PlanOrderView view, final int position, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.reshData(body).enqueue(new CallBack<PlanOrderReshDto>() {
            @Override
            public void success(PlanOrderReshDto response) {
                view.dissLoading();
                view.reshPlanData(response,position);

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

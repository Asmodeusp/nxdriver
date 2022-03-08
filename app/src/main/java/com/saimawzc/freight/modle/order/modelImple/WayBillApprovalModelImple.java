package com.saimawzc.freight.modle.order.modelImple;



import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.order.OrderDelationDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.WayBillApprovalModel;
import com.saimawzc.freight.view.order.WaybillApprovalView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.order.OrderDelationListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 预运单审核实现
 * **/
public class WayBillApprovalModelImple extends BasEModeImple implements WayBillApprovalModel {

    @Override
    public void approval(final WaybillApprovalView view, final BaseListener listener, String id, int status, final String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("type",type);
            jsonObject.put("status",status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.roderAssign(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                if(type.equals("1")){
                    view.Toast("确认指派成功");
                }else {
                    //view.Toast("拒绝指派成功");
                }
                listener.successful();


            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }


    /****
     * 获取订单详情
     * **/
    @Override
    public void getOrderDelation(final WaybillApprovalView view, final OrderDelationListener listener, String id,String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        if(type.equals("1")){//计划订单
            orderApi.getPlanOrderDelation(body).enqueue(new CallBack<OrderDelationDto>() {
                @Override
                public void success(OrderDelationDto response) {
                    view.dissLoading();
                    listener.back(response);

                }
                @Override
                public void fail(String code, String message) {
                    view.dissLoading();
                    view.Toast(message);
                }
            });
        }else if(type.equals("2")) {//预运单
            orderApi.getcysWayBillOrderDelation(body).enqueue(new CallBack<OrderDelationDto>() {
                @Override
                public void success(OrderDelationDto response) {
                    view.dissLoading();
                    listener.back(response);

                }
                @Override
                public void fail(String code, String message) {
                    view.dissLoading();
                    view.Toast(message);
                }
            });
        }



    }

    @Override
    public void getsjOrderDelation(final WaybillApprovalView view,final OrderDelationListener listener, String id, String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        if(type.equals("1")){//计划订单
            orderApi.getSjPlanOrderDelation(body).enqueue(new CallBack<OrderDelationDto>() {
                @Override
                public void success(OrderDelationDto response) {
                    view.dissLoading();
                    listener.back(response);

                }
                @Override
                public void fail(String code, String message) {
                    view.dissLoading();
                    view.Toast(message);
                }
            });
        }else if(type.equals("2")) {//预运单
            orderApi.getsjWayBillOrderDelation(body).enqueue(new CallBack<OrderDelationDto>() {
                @Override
                public void success(OrderDelationDto response) {
                    view.dissLoading();
                    listener.back(response);

                }

                @Override
                public void fail(String code, String message) {
                    view.dissLoading();
                    view.Toast(message);
                }
            });
        }
    }
}

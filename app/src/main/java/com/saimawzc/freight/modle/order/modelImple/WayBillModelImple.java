package com.saimawzc.freight.modle.order.modelImple;
import android.text.TextUtils;

import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.order.bill.WayBillDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.WayBillModel;
import com.saimawzc.freight.view.order.WayBillView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.order.WayBillListener;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class WayBillModelImple extends BasEModeImple implements WayBillModel {




    @Override
    public void getWayBill(final WayBillView view,final WayBillListener listener, int page
    ,String searchType,String searchValue) {
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
        orderApi.getWayBillList(body).enqueue(new CallBack<WayBillDto>() {
            @Override
            public void success(WayBillDto response) {
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

    /**
     * 承运商删除
     * **/
    @Override
    public void delete(final WayBillView view, final WayBillListener listener, String id) {
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
        orderApi.cysdelete(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
               listener.successful();
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void getsjWayBill(final WayBillView view, final WayBillListener listener, int page, String searchType, String searchValue) {
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
        orderApi.getsjWayBillList(body).enqueue(new CallBack<WayBillDto>() {
            @Override
            public void success(WayBillDto response) {
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
    public void sjdelete(final WayBillView view, final WayBillListener listener, String id) {
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
        orderApi.sjdelete(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                listener.successful();
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

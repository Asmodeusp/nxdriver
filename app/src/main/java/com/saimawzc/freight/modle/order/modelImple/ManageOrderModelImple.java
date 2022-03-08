package com.saimawzc.freight.modle.order.modelImple;
import android.text.TextUtils;

import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.order.ManageListDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.ManageOrderModel;
import com.saimawzc.freight.view.order.ManageOrderView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.order.ManageOrderListener;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ManageOrderModelImple extends BasEModeImple implements ManageOrderModel {




    @Override
    public void getCarList(final ManageOrderView view, final ManageOrderListener listener,
                           int page,String searchType,String searchValue) {
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
        orderApi.getManageOrderList(body).enqueue(new CallBack<ManageListDto>() {
            @Override
            public void success(ManageListDto response) {
                view.dissLoading();
                view.isLastPage(response.isLastPage());
                listener.getManageOrderList(response.getList());
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void delete(final ManageOrderView view,final ManageOrderListener listener, String id) {
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
        orderApi.cysdeletemanage(body).enqueue(new CallBack<EmptyDto>() {
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
    public void getsjCarList(final ManageOrderView view, final ManageOrderListener listener, int page, String searchType, String searchValue) {
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
        orderApi.getsjManageOrderList(body).enqueue(new CallBack<ManageListDto>() {
            @Override
            public void success(ManageListDto response) {
                view.dissLoading();
                view.isLastPage(response.isLastPage());
                listener.getManageOrderList(response.getList());
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void sjdelete(final ManageOrderView view, final ManageOrderListener listener, String id) {
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
        orderApi.sjdeletemanage(body).enqueue(new CallBack<EmptyDto>() {
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

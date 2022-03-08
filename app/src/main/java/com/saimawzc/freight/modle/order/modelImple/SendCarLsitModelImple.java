package com.saimawzc.freight.modle.order.modelImple;
import android.text.TextUtils;

import com.saimawzc.freight.dto.order.SendCarDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.SendCarLsitModel;
import com.saimawzc.freight.view.order.SendCarListView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.order.SendCarListListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SendCarLsitModelImple extends BasEModeImple implements SendCarLsitModel {


    @Override
    public void getSendCarLsit(final SendCarListView view, final SendCarListListener listener,int page,
                               String type,String searchType,String searchValue) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("status",type);
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
        orderApi.getSendCarList(body).enqueue(new CallBack<SendCarDto>() {
            @Override
            public void success(SendCarDto response) {
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
}

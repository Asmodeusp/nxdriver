package com.saimawzc.freight.modle.sendcar.imple;
import android.text.TextUtils;

import com.saimawzc.freight.dto.sendcar.CompleteExecuteDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.sendcar.model.CompleteExecuteModel;
import com.saimawzc.freight.view.sendcar.CompleteExecuteView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.send.CompleteExecuteListListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CompeleteExecuteModelImple extends BasEModeImple implements CompleteExecuteModel {





    @Override
    public void getSendLsit(final CompleteExecuteView view, final CompleteExecuteListListener listener, int page, String type, final String value, String startTime, String endTime, int status) {
        view.showLoading();
        view.stopResh();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            if(!TextUtils.isEmpty(type)){
                jsonObject.put("type",type);
            }
            jsonObject.put("name",value);

            jsonObject.put("startTime",startTime);
            jsonObject.put("endTime",endTime);
            if(status!=0){
                jsonObject.put("completeStatus",status);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getCompelete(body).enqueue(new CallBack<CompleteExecuteDto>() {
            @Override
            public void success(CompleteExecuteDto response) {
                view.dissLoading();
                view.stopResh();
                view.isLastPage(response.isLastPage());
                view.getSendCarList(response.getList());
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

package com.saimawzc.freight.modle.order.modelImple;

import android.content.Context;

import com.saimawzc.freight.base.CameraListener;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.order.error.ErrorReportDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.bidd.ErrorModel;
import com.saimawzc.freight.view.order.error.ErrorView;
import com.saimawzc.freight.weight.utils.CameraDialogUtil;
import com.saimawzc.freight.weight.utils.http.CallBack;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ErrorModelImple extends BasEModeImple implements ErrorModel {

    @Override
    public void getErrorType(final ErrorView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getErrorList(body).enqueue(new CallBack<List<ErrorReportDto>>() {
            @Override
            public void success(List<ErrorReportDto> reportDtos) {
                view.dissLoading();
                view.getErrorType(reportDtos);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void submitError(final ErrorView view, String id, String exceptionTypeId, String exceptionName,
                            String exceptionImage, String location) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("tmsWaybillId",id);
            jsonObject.put("exceptionTypeId",exceptionTypeId);
            jsonObject.put("exceptionName",exceptionName);
            jsonObject.put("exceptionImage",exceptionImage);
            jsonObject.put("location",location);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.addErrorReport(body).enqueue(new CallBack<EmptyDto>() {
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
    private CameraDialogUtil cameraDialogUtil;
    @Override
    public void showCamera(final ErrorView view, Context context) {
        if(cameraDialogUtil==null){
            cameraDialogUtil=new CameraDialogUtil(context);
        }
        cameraDialogUtil.showDialog(new CameraListener() {//type
            @Override
            public void takePic() {
                view.successful(0);
                cameraDialogUtil.dialog.dismiss();

            }
            @Override
            public void chooseLocal() {
                view.successful(1);
                cameraDialogUtil.dialog.dismiss();

            }
            @Override
            public void cancel() {
                cameraDialogUtil.dialog.dismiss();
            }
        });
    }
}

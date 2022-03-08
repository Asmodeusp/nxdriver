package com.saimawzc.freight.modle.sendcar.imple;

import android.content.Context;
import android.text.TextUtils;
import com.baidu.location.BDLocation;
import com.saimawzc.freight.base.CameraListener;
import com.saimawzc.freight.dto.my.RouteDto;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.sendcar.TrantDto;
import com.saimawzc.freight.dto.sendcar.TrantSamllOrderDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.sendcar.model.TrantModel;
import com.saimawzc.freight.view.sendcar.DriverTransportView;
import com.saimawzc.freight.weight.utils.CameraDialogUtil;
import com.saimawzc.freight.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TrantModelImple extends BasEModeImple implements TrantModel {

    @Override
    public void getTrant(final DriverTransportView view, String id, final int type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("type",type);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getTrant(body).enqueue(new CallBack<TrantDto>() {
            @Override
            public void success(TrantDto response) {
                view.dissLoading();
                view.getData(response,type);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void daka(final DriverTransportView view, String id,
                     String type, BDLocation location, String pic) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("type",type);
            if(location!=null){
                jsonObject.put("location",location.getLongitude()+","+location.getLatitude());
                jsonObject.put("address",location.getAddress().address);
            }
            jsonObject.put("picture",pic);
            jsonObject.put("eqType",1);//1android  2 苹果

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.trantDaka(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                view.oncomplete();

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
                view.getCode(code);

            }
        });
    }

    @Override
    public void roulete(final DriverTransportView view, String id, final int type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getRoute(body).enqueue(new CallBack<RouteDto>() {
            @Override
            public void success(RouteDto response) {
                view.dissLoading();
                view.getRolte(response,type);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    /****
     * 获取预运单路径规划
     * **/
    @Override
    public void getWayBillRolete(final DriverTransportView view, String id,String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getSmallOrderRulet(body).enqueue(new CallBack<TrantSamllOrderDto>() {
            @Override
            public void success(TrantSamllOrderDto response) {
                view.dissLoading();
                view.getSmallOrderRolte(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void isFenceClock(final DriverTransportView view, String id,
                             String location, final String operType) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("location",location);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getIsFeece(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                view.isFence(1,"",operType);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                //view.Toast(message);
                view.isFence(2,message,operType);
            }
        });
    }
    private CameraDialogUtil cameraDialogUtil;
    @Override
    public void showCamera(Context context,final DriverTransportView view) {
        if(cameraDialogUtil==null){
            cameraDialogUtil=new CameraDialogUtil(context);
        }
        if(context==null){
            return;
        }
        cameraDialogUtil.showDialog(new CameraListener() {//type
            @Override
            public void takePic() {
                view.showCamera(0);
                cameraDialogUtil.dialog.dismiss();
            }
            @Override
            public void chooseLocal() {
                view.showCamera(1);
                cameraDialogUtil.dialog.dismiss();
            }
            @Override
            public void cancel() {
                view.showCamera(100);
                cameraDialogUtil.dialog.dismiss();
            }
        });
    }
}

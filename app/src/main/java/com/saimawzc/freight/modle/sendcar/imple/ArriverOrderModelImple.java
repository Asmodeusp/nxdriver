package com.saimawzc.freight.modle.sendcar.imple;

import android.content.Context;

import com.saimawzc.freight.dto.order.SignWeightDto;
import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.base.CameraListener;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.sendcar.ArriverOrderDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.sendcar.model.ArriverOrderModel;
import com.saimawzc.freight.view.sendcar.ArriverOrderView;
import com.saimawzc.freight.weight.utils.CameraDialogUtil;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ArriverOrderModelImple extends BasEModeImple implements ArriverOrderModel {



    @Override
    public void getData(final ArriverOrderView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getArriverDto(body).enqueue(new CallBack<ArriverOrderDto>() {
            @Override
            public void success(ArriverOrderDto response) {
                view.dissLoading();
                view.getData(response.getMaterialsList());
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
    public void showCamera(Context context,  final BaseListener listener) {
        if(cameraDialogUtil==null){
            cameraDialogUtil=new CameraDialogUtil(context);
        }
        cameraDialogUtil.showDialog(new CameraListener() {//type
            @Override
            public void takePic() {
                listener.successful(0);
                cameraDialogUtil.dialog.dismiss();

            }
            @Override
            public void chooseLocal() {
                listener.successful(1);
                cameraDialogUtil.dialog.dismiss();

            }
            @Override
            public void cancel() {
                cameraDialogUtil.dialog.dismiss();
            }
        });
    }

    @Override
    public void daka(final ArriverOrderView view, String id, String type,String adress,String location,
                     String pic, List<ArriverOrderDto.materialsDto> dtos,String distance,int  positioningMode,String tuneLocation) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("type",type);
            jsonObject.put("distance",distance);
            if(location!=null){
                jsonObject.put("location",location);
                jsonObject.put("address",adress);
            }
            jsonObject.put("picture",pic);
            jsonObject.put("eqType",1);
            jsonObject.put("positioningMode",positioningMode+"");//1.自动定位 2.手动定位
            jsonObject.put("tuneLocation",tuneLocation);//调整后坐标

            JSONArray array=new JSONArray();
            if(dtos!=null){
                for(int i=0;i<dtos.size();i++){
                    JSONObject temp=new JSONObject();
                    temp.put("id",dtos.get(i).getId());
                    if(type.equals("7")){
                        temp.put("signWeight",dtos.get(i).getSignWeight());
                    }else {
                        temp.put("signWeight",dtos.get(i).getWeighing());
                    }

                    array.put(temp);
                }
            }
            jsonObject.put("confirmList",array);
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
                view.oncomplete(code);
            }
        });
    }

    @Override
    public void daka(final ArriverOrderView view, String id, String pic,
                     List<ArriverOrderDto.materialsDto> dtos,String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("picture",pic);
            jsonObject.put("eqType",1);
            jsonObject.put("type",type);

            JSONArray array=new JSONArray();
            if(dtos!=null){
                for(int i=0;i<dtos.size();i++){
                    JSONObject temp=new JSONObject();
                    temp.put("id",dtos.get(i).getId());
                    if(type.equals("7")){
                        temp.put("signWeight",dtos.get(i).getSignWeight());
                    }else {
                        temp.put("signWeight",dtos.get(i).getWeighing());
                    }
                   // temp.put("signWeight",dtos.get(i).getSignWeight());
                    array.put(temp);
                }
            }
            jsonObject.put("confirmList",array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.retrantDaka(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                view.oncomplete();

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
                view.oncomplete(code);
            }
        });
    }

    @Override
    public void getSignWeight(final ArriverOrderView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.getSignWeight(body).enqueue(new CallBack<SignWeightDto>() {
            @Override
            public void success(SignWeightDto response) {
                view.dissLoading();
                view.getSignWeiht(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
                view.getSignWeiht(null);
            }
        });
    }

    @Override
    public void isFenceClock(final ArriverOrderView view, String id, String location) {
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
                view.isFence(1,"");
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                //view.Toast(message);
                view.isFence(2,message);
            }
        });
    }

    @Override
    public void isErrorPic(final ArriverOrderView view, String id, String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("waybillId",id);
            jsonObject.put("warnType",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.uplaodStayTime(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
            }
        });
    }
}

package com.saimawzc.freight.modle.mine.car.ship;

import android.content.Context;

import com.saimawzc.freight.base.CameraListener;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.dto.my.ShipTypeDo;
import com.saimawzc.freight.dto.my.car.ship.SearchShipDto;
import com.saimawzc.freight.dto.my.car.ship.ShipIsRegsister;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.mine.car.ship.ResisterShipView;
import com.saimawzc.freight.weight.utils.CameraDialogUtil;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
/**
 * Created by Administrator on 2020/8/6.
 */
public class ResisterShipModelImple extends BasEModeImple implements ResisterShipModel {

    private CameraDialogUtil cameraDialogUtil;

    @Override
    public void identification(final ResisterShipView view) {

        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        UserInfoDto loginDo= Hawk.get(PreferenceKey.USER_INFO);
        if(loginDo==null){
            return;
        }
        try {

            jsonObject.put("shipLength",view.shipLength());
            jsonObject.put("tranPermit",view.tranPermit());
            jsonObject.put("tranNo",view.tranNo());//
            jsonObject.put("businessName",view.businessName());
            jsonObject.put("drivingLicense",view.drivingLicense());
            jsonObject.put("sideView",view.sideView());
            jsonObject.put("shipWidth",view.shipWidth());
            jsonObject.put("shipHeight", view.shipHeight());
            jsonObject.put("shipName", view.shipName());
            jsonObject.put("shipNumberId",view.shipNumberId());
            jsonObject.put("shipType",view.shipType());

            jsonObject.put("shipVolume",view.shipVolume());

            jsonObject.put("invitEnter",view.invitEnter());//是否允许被添加


            jsonObject.put("ifQualification",view.ifQualification());
            jsonObject.put("allowAdd",view.allowAdd());

            jsonObject.put("registerLocation",view.location());



        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.addShip(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
               view.oncomplete();
                view.dissLoading();
            }
            @Override
            public void fail(String code, String message) {
               view.Toast(message);
                view.dissLoading();
            }
        });



    }


    @Override
    public void showCamera(Context context, final int type, final ResisterShipView view) {
        if(cameraDialogUtil==null){
            cameraDialogUtil=new CameraDialogUtil(context);
        }
        cameraDialogUtil.showDialog(new CameraListener() {//type
            @Override
            public void takePic() {
                if(type==0){
                    view.OnDealCamera(0);//道路运输证
                }else if(type==1){
                    view.OnDealCamera(1);//行驶证
                }else if(type==2){
                    view.OnDealCamera(2);//车辆信息
                }
                cameraDialogUtil.dialog.dismiss();
            }
            @Override
            public void chooseLocal() {
                if(type==0){
                    view.OnDealCamera(3);//道路运输证
                }else if(type==1) {
                    view.OnDealCamera(4);//行驶证
                } else if(type==2){
                    view.OnDealCamera(5);//车辆信息
                }
                cameraDialogUtil.dialog.dismiss();
            }
            @Override
            public void cancel() {
                cameraDialogUtil.dialog.dismiss();

            }
        });
    }

    @Override
    public void dissCamera() {
        if(cameraDialogUtil!=null){
            cameraDialogUtil.dialog.dismiss();
        }
    }

    /***
     * 获取船的类型
     * **/
    @Override
    public void getShipType(final ResisterShipView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getShipType(body).enqueue(new CallBack<List<ShipTypeDo>>() {
            @Override
            public void success(List<ShipTypeDo> response) {
                view.dissLoading();
                view.getShipType(response);
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void getShipInfo(final ResisterShipView view,  String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getShipInfo(body).enqueue(new CallBack<SearchShipDto>() {
            @Override
            public void success(SearchShipDto response) {
                view.dissLoading();
                view.getShipInfo(response);

            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });

    }

    @Override
    public void isregster(final ResisterShipView view,  String carNum) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("shipNumberId",carNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.isShipChange(body).enqueue(new CallBack<ShipIsRegsister>() {
            @Override
            public void success(ShipIsRegsister response) {
                view.dissLoading();
                view.isResister(response);
                //listener.isRegsister(response);
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void ismodify(final ResisterShipView view, final String shipId) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        UserInfoDto loginDo= Hawk.get(PreferenceKey.USER_INFO);
        if(loginDo==null){
            return;
        }
        try {

            jsonObject.put("id",shipId);
            jsonObject.put("shipLength",view.shipLength());
            jsonObject.put("tranPermit",view.tranPermit());
            jsonObject.put("tranNo",view.tranNo());//
            jsonObject.put("businessName",view.businessName());
            jsonObject.put("drivingLicense",view.drivingLicense());
            jsonObject.put("sideView",view.sideView());
            jsonObject.put("shipWidth",view.shipWidth());
            jsonObject.put("shipHeight", view.shipHeight());
            jsonObject.put("shipName", view.shipName());
            jsonObject.put("shipNumberId",view.shipNumberId());
            jsonObject.put("shipType",view.shipType());

            jsonObject.put("shipVolume",view.shipVolume());

            jsonObject.put("invitEnter",view.invitEnter());//是否允许被添加


            jsonObject.put("ifQualification",view.ifQualification());
            jsonObject.put("allowAdd",view.allowAdd());
            jsonObject.put("registerLocation",view.location());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.modifyShip(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.oncomplete();
                view.dissLoading();
            }
            @Override
            public void fail(String code, String message) {
                view.Toast(message);
                view.dissLoading();
            }
        });

    }


}

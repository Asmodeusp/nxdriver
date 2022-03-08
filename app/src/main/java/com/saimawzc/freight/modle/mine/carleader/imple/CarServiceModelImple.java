package com.saimawzc.freight.modle.mine.carleader.imple;

import android.content.Context;
import com.saimawzc.freight.base.CameraListener;
import com.saimawzc.freight.dto.AddServiceDto;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.my.CarBrandDto;
import com.saimawzc.freight.dto.my.CarTypeDo;
import com.saimawzc.freight.dto.my.carleader.CarServiceSfInfoDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.mine.carleader.CarServiceModel;
import com.saimawzc.freight.view.mine.carleader.CarServiceView;
import com.saimawzc.freight.weight.utils.CameraDialogUtil;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.car.CarBrandListener;
import com.saimawzc.freight.weight.utils.listen.car.CarTypeListener;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
/**
 * Created by Administrator on 2020/7/31.
 *
 */

public class CarServiceModelImple extends BasEModeImple implements CarServiceModel {


    @Override
    public void edit(final CarServiceView view) {
        view.showLoading();

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",view.id());
            jsonObject.put("carId",view.carId());
            jsonObject.put("cdzId",view.cdzId());
            jsonObject.put("sjId",view.sjId());
            jsonObject.put("clsyr",view.clsyr());
            jsonObject.put("cycc",view.cycc());
            jsonObject.put("cycph",view.cycph());
            jsonObject.put("cycsfgk",view.cycsfgk());
            jsonObject.put("cycsjsyr",view.cycsjsyr());
            jsonObject.put("cycsyrlxfs",view.cycsyrlxfs());
            jsonObject.put("cycx",view.cycx());
            jsonObject.put("cycxszfjBos",view.cycxszfjBos());
            jsonObject.put("cyczpfjBos",view.cyczpfjBos());
            jsonObject.put("cypp",view.cypp());
            jsonObject.put("cyzz",view.cyzz());
            jsonObject.put("czsmgkfjBos",view.czsmgkfjBos());
            jsonObject.put("fwfsfzjhm",view.fwfsfzjhm());
            jsonObject.put("fwfsfzjlx",view.fwfsfzjlx());
            jsonObject.put("fwfuuid",view.fwfuuid());
            jsonObject.put("fwfxm",view.fwfxm());
            jsonObject.put("jszfjBos",view.jszfjBos());
            jsonObject.put("jszjhm",view.jszjhm());
            jsonObject.put("khrsfzjhm",view.khrsfzjhm());
            jsonObject.put("khrxm",view.khrxm());
            jsonObject.put("khyh",view.khyh());
            jsonObject.put("ptzcsj",view.ptzcsj());
            jsonObject.put("qysmgkfjBos",view.qysmgkfjBos());
            jsonObject.put("role",view.role());
            jsonObject.put("roleId",view.roleId());
            jsonObject.put("skzh",view.skzh());
            jsonObject.put("smrzfjBos",view.smrzfjBos());
            jsonObject.put("ssdq",view.ssdq());
            jsonObject.put("xszjhm",view.xszjhm());
            jsonObject.put("yddh",view.yddh());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.addCarService(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
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
    public void showCamera(final CarServiceView view, Context context, final int type) {
        if(cameraDialogUtil==null){
            cameraDialogUtil=new CameraDialogUtil(context);
        }
        cameraDialogUtil.showDialog(new CameraListener() {//type
            @Override
            public void takePic() {

                if(type==0){
                    view.OnDealCamera(0);
                }else if(type==1){
                    view.OnDealCamera(1);
                }else if(type==2){
                    view.OnDealCamera(2);
                }else if(type==3){
                    view.OnDealCamera(3);
                }else if(type==4){
                    view.OnDealCamera(4);
                }else if(type==5){
                    view.OnDealCamera(5);
                }
                cameraDialogUtil.dialog.dismiss();

            }
            @Override
            public void chooseLocal() {
                if(type==0){
                    view.OnDealCamera(6);
                }else if(type==1){
                    view.OnDealCamera(7);
                }else if(type==2){
                    view.OnDealCamera(8);
                }else if(type==3){
                    view.OnDealCamera(9);
                }else if(type==4){
                    view.OnDealCamera(10);
                }else if(type==5){
                    view.OnDealCamera(11);
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
    public void getCarType(final CarServiceView view, final CarTypeListener listener) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getCarType(body).enqueue(new CallBack<List<CarTypeDo>>() {
            @Override
            public void success(List<CarTypeDo> response) {
                view.dissLoading();
                listener.callbacktype(response);
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void getBrand(final CarServiceView view, final CarBrandListener listener, String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("carTypeName",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getCarBrand(body).enqueue(new CallBack<List<CarBrandDto>>() {
            @Override
            public void success(List<CarBrandDto> response) {
                view.dissLoading();
                listener.callbackbrand(response);
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void getSfInfo(final CarServiceView view, String carId,String cdzId,String siId) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("carId",carId);
            jsonObject.put("cdzId",cdzId);
            jsonObject.put("sjId",siId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getSFiNFO(body).enqueue(new CallBack<CarServiceSfInfoDto>() {
            @Override
            public void success(CarServiceSfInfoDto response) {
                view.dissLoading();
                view.getSfInfo(response);
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

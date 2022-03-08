package com.saimawzc.freight.modle.sendcar.imple;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.my.carleader.LeaderEmptyDto;
import com.saimawzc.freight.dto.my.queue.ChooseQueDto;
import com.saimawzc.freight.dto.sendcar.SendCarDelatiodto;
import com.saimawzc.freight.dto.sendcar.WaitExecuteDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.sendcar.model.SendCarDelationModel;
import com.saimawzc.freight.ui.login.LoginActivity;
import com.saimawzc.freight.ui.my.pubandservice.ServiceSubmitActivity;
import com.saimawzc.freight.view.sendcar.SendCarDelationView;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.QueueDialog;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.send.SendCarDelationListener;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SendCarDelationModelImple extends BasEModeImple implements SendCarDelationModel {


    private NormalDialog dialog;


    @Override
    public void getSendCarDelation(final SendCarDelationView view,
                                   final SendCarDelationListener listener, String id, final int type) {

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
            tmsApi.getSendCarDelation(body).enqueue(new CallBack<SendCarDelatiodto>() {
                @Override
                public void success(SendCarDelatiodto response) {
                    view.dissLoading();
                    view.getDelation(response,type);
                }
                @Override
                public void fail(String code, String message) {
                    view.dissLoading();
                    view.Toast(message);
                }
            });
    }

    @Override
    public void startTask(final SendCarDelationView view, final String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        tmsApi.startTask(body).enqueue(new CallBack<List<LeaderEmptyDto>>() {
            @Override
            public void success(List<LeaderEmptyDto> response) {
                view.oncomplete();

            }
            @Override
            public void fail(String code, final String message) {
                view.dissLoading();
                if(code.equals("5002")){
                    view.dissLoading();
                    List<ChooseQueDto> mDatas=new ArrayList<>();
                    try {
                        JSONArray array=new JSONArray(message);
                        for(int i=0;i<array.length();i++){
                            ChooseQueDto queDto=new ChooseQueDto();
                            queDto.setId(array.getJSONObject(i).getString("id"));
                            queDto.setPhone(array.getJSONObject(i).getString("phone"));
                            queDto.setName(array.getJSONObject(i).getString("name"));
                            mDatas.add(queDto);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                   final QueueDialog queueDialog=new QueueDialog(view.getContect(),mDatas);
                    queueDialog.show();
                    queueDialog.setDialogListener(new QueueDialog.OnTimeoutDialogClickListener() {
                        @Override
                        public void onRecollect() {
                            queueDialog.dismiss();
                        }
                        @Override
                        public void onReturn(final String cdzId,String name,String phone) {
                            queueDialog.dismiss();
                            dialog = new NormalDialog(view.getContect()).isTitleShow(false)
                                    .content("您确定任务完成后运费支付给车队长"+name+"  "+phone+"?")
                                    .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                                    .btnNum(2).btnText("取消", "确定");
                            dialog.setOnBtnClickL(
                                    new OnBtnClickL() {
                                        @Override
                                        public void onBtnClick() {
                                            if(!view.getContect().isDestroy(view.getContect())){
                                                dialog.dismiss();
                                            }
                                        }
                                    },
                                    new OnBtnClickL() {
                                        @Override
                                        public void onBtnClick() {
                                            view.showLoading();
                                            JSONObject jsonObject=new JSONObject();
                                            try {
                                                jsonObject.put("id",id);
                                                jsonObject.put("cdzId",cdzId);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
                                            RequestBody body = RequestBody.create(JSON,jsonObject.toString());
                                            tmsApi.addCarQueue(body).enqueue(new CallBack<EmptyDto>() {
                                                @Override
                                                public void success(EmptyDto response) {
                                                    view.dissLoading();
                                                    view.oncomplete();
                                                }
                                                @Override
                                                public void fail(String code, String message) {
                                                    view.Toast(message);
                                                    view.dissLoading();
                                                }
                                            });
                                            if(!view.getContect().isDestroy(view.getContect())){
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                            dialog.show();
                        }
                    });

                }else if(code.equals("5003")){
                    dialog = new NormalDialog(view.getContect()).isTitleShow(false)
                            .content("您还未完善服务方信息，是否立即完善?")
                            .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                            .btnNum(2).btnText("取消", "确定");
                    dialog.setOnBtnClickL(
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    if(!view.getContect().isDestroy(view.getContect())){
                                        dialog.dismiss();
                                    }
                                }
                            },
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    Bundle bundle=new Bundle();
                                    bundle.putString("carNo",message);
                                   view.getContect().readyGo(ServiceSubmitActivity.class,bundle);
                                    if(!view.getContect().isDestroy(view.getContect())){
                                        dialog.dismiss();
                                    }
                                }
                            });
                    dialog.show();
                }else {
                    view.Toast(message);
                }

            }
        });
    }
}

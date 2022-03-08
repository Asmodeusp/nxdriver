package com.saimawzc.freight.modle.sendcar.imple;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.LcInfoDto;
import com.saimawzc.freight.dto.my.carleader.LeaderEmptyDto;
import com.saimawzc.freight.dto.my.queue.ChooseQueDto;
import com.saimawzc.freight.dto.sendcar.WaitExecuteDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.sendcar.model.WaitExecuteModel;
import com.saimawzc.freight.ui.my.pubandservice.ServiceSubmitActivity;
import com.saimawzc.freight.view.sendcar.WaitExecuteView;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.QueueDialog;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.send.WaitExecuteListListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class WaitExecuteModelImple extends BasEModeImple implements WaitExecuteModel {

    private NormalDialog dialog;

    @Override
    public void getSendLsit(final WaitExecuteView view, final WaitExecuteListListener listener,
                            int page, String type, final String value) {
        view.showLoading();
        view.stopResh();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageNum", page);
            if (!TextUtils.isEmpty(type)) {
                jsonObject.put("type", type);
            }
            jsonObject.put("name", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        tmsApi.getWaitdate(body).enqueue(new CallBack<WaitExecuteDto>() {
            @Override
            public void success(WaitExecuteDto response) {
                view.dissLoading();
                view.isLastPage(response.isLastPage());
                view.getSendCarList(response.getList());
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void startTask(final WaitExecuteView view, final String id, final int position) {
        view.showLoading();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        tmsApi.startTask(body).enqueue(new CallBack<List<LeaderEmptyDto>>() {
            @Override
            public void success(List<LeaderEmptyDto> response) {
                view.dissLoading();
                view.oncomplete(position);
            }

            @Override
            public void fail(String code, final String message) {
                view.dissLoading();
                if (code.equals("5002")) {
                    view.dissLoading();
                    List<ChooseQueDto> mDatas = new ArrayList<>();
                    try {
                        JSONArray array = new JSONArray(message);
                        for (int i = 0; i < array.length(); i++) {
                            ChooseQueDto queDto = new ChooseQueDto();
                            queDto.setId(array.getJSONObject(i).getString("id"));
                            queDto.setPhone(array.getJSONObject(i).getString("phone"));
                            queDto.setName(array.getJSONObject(i).getString("name"));
                            mDatas.add(queDto);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    final QueueDialog queueDialog = new QueueDialog(view.getcontect(), mDatas);
                    queueDialog.show();
                    queueDialog.setDialogListener(new QueueDialog.OnTimeoutDialogClickListener() {
                        @Override
                        public void onRecollect() {
                            queueDialog.dismiss();
                        }

                        @Override
                        public void onReturn(final String cdzId, String name, String phone) {
                            Log.e("msg", "选择的ID");
                            queueDialog.dismiss();
                            dialog = new NormalDialog(view.getcontect()).isTitleShow(false)
                                    .content("您确定任务完成后运费支付给车队长" + name + "  " + phone + "?")
                                    .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                                    .btnNum(2).btnText("取消", "确定");
                            dialog.setOnBtnClickL(
                                    new OnBtnClickL() {
                                        @Override
                                        public void onBtnClick() {
                                            if (!view.getcontect().isDestroy(view.getcontect())) {
                                                dialog.dismiss();
                                            }
                                        }
                                    },
                                    new OnBtnClickL() {
                                        @Override
                                        public void onBtnClick() {
                                            view.showLoading();
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("id", id);
                                                jsonObject.put("cdzId", cdzId);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                                            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                                            tmsApi.addCarQueue(body).enqueue(new CallBack<EmptyDto>() {
                                                @Override
                                                public void success(EmptyDto response) {
                                                    view.dissLoading();
                                                    view.oncomplete(position);
                                                }

                                                @Override
                                                public void fail(String code, String message) {
                                                    view.Toast(message);
                                                    view.dissLoading();

                                                }
                                            });

                                            if (!view.getcontect().isDestroy(view.getcontect())) {
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                            dialog.show();


                        }
                    });
                } else if (code.equals("5003")) {
                    dialog = new NormalDialog(view.getcontect()).isTitleShow(false)
                            .content("您还未完善服务方信息，是否立即完善?")
                            .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                            .btnNum(2).btnText("取消", "确定");
                    dialog.setOnBtnClickL(
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    if (!view.getcontect().isDestroy(view.getcontect())) {
                                        dialog.dismiss();
                                    }
                                }
                            },
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("carNo", message);
                                    Log.e("msg", "传递" + message);
                                    view.getcontect().readyGo(ServiceSubmitActivity.class, bundle);
                                    if (!view.getcontect().isDestroy(view.getcontect())) {
                                        dialog.dismiss();
                                    }
                                }
                            });
                    dialog.show();
                } else {
                    view.Toast(message);
                }
            }
        });
    }

    @Override
    public void getLcInfoDto(final WaitExecuteView view, WaitExecuteListListener listener, String lcbh, String dispatchCarId, String czbm,String companyId) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(lcbh)) {
                jsonObject.put("lcbh", lcbh);
            }

            if (!TextUtils.isEmpty(dispatchCarId)) {
                jsonObject.put("dispatchCarNo", dispatchCarId);
            }
            if (!TextUtils.isEmpty(czbm)) {
                jsonObject.put("czbm", czbm);
            }
            if (!TextUtils.isEmpty(companyId)) {
                jsonObject.put("companyId", companyId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        tmsApi.queryLcInfo(body).enqueue(new CallBack<LcInfoDto>() {
            @Override
            public void success(LcInfoDto response) {
                view.dissLoading();
                view.getLcInfoDto(response);
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void siloScanUnlock(final WaitExecuteView view, WaitExecuteListListener listener, String dispatchCarId, String dispatchCarNo, String lcbh) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(lcbh)) {
                jsonObject.put("lcbh", lcbh);
            }
            if (!TextUtils.isEmpty(dispatchCarId)) {
                jsonObject.put("dispatchCarNo", dispatchCarId);
            }
            if (!TextUtils.isEmpty(dispatchCarNo)) {
                jsonObject.put("dispatchCarNo", dispatchCarNo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        tmsApi.siloScanUnlock(body).enqueue(new CallBack<LcInfoDto>() {
            @Override
            public void success(LcInfoDto response) {
                view.dissLoading();
                view.siloScanUnlock(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void siloScanLock(final WaitExecuteView view, WaitExecuteListListener listener, String dispatchCarId, String dispatchCarNo, String lcbh) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(lcbh)) {
                jsonObject.put("lcbh", lcbh);
            }
            if (!TextUtils.isEmpty(dispatchCarId)) {
                jsonObject.put("dispatchCarId", dispatchCarId);
            }
            if (!TextUtils.isEmpty(dispatchCarNo)) {
                jsonObject.put("dispatchCarNo", dispatchCarNo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        tmsApi.siloScanLock(body).enqueue(new CallBack<LcInfoDto>() {
            @Override
            public void success(LcInfoDto response) {
                view.dissLoading();
                view.siloScanLock(response);
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }


}

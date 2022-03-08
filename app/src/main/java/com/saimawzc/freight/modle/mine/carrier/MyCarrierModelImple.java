package com.saimawzc.freight.modle.mine.carrier;

import com.saimawzc.freight.dto.my.carrier.CarrierPageDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.mine.carrier.MyCarrierView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.carrier.MyCarrierListener;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/10.
 * 我的司机
 */

public class MyCarrierModelImple extends BasEModeImple implements MyCarrierModel {

    @Override
    public void getCarrier(final MyCarrierView view, final MyCarrierListener listener, int type, int page,String carNo) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            jsonObject.put("pageSize",20);
            jsonObject.put("state",type);//state状态 （1.以添加 2.待确认）
            jsonObject.put("userAccount",carNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getMyCarrier(body).enqueue(new CallBack<CarrierPageDto>() {
            @Override
            public void success(CarrierPageDto response) {
                view.dissLoading();
                listener.callbackbrand(response);
                view.stopRefresh();
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
                view.stopRefresh();
            }
        });
    }
}

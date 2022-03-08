package com.saimawzc.freight.modle.wallet.imple;
import android.text.TextUtils;
import android.util.Log;

import com.saimawzc.freight.dto.wallet.TradeChooseDto;
import com.saimawzc.freight.dto.wallet.TradeDelationDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.wallet.TradeDealtionModel;
import com.saimawzc.freight.view.wallet.TradeDelationView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TradeDelationModelImple extends BasEModeImple implements TradeDealtionModel {



    @Override
    public void getTradeList(final TradeDelationView view,int page, TradeChooseDto dto) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("pageNum",page);
            if(dto!=null){
                if(!TextUtils.isEmpty(dto.getStartTime())){
                    jsonObject.put("startTime",dto.getStartTime());
                }
                if(!TextUtils.isEmpty(dto.getEndTime())){
                    jsonObject.put("endTime",dto.getEndTime());
                }
                if(!TextUtils.isEmpty(dto.getMinMoney())){
                    jsonObject.put("minMoney",dto.getMinMoney());
                }
                if(!TextUtils.isEmpty(dto.getMaxMoney())){
                    jsonObject.put("maxMoney",dto.getMaxMoney());
                }
                if(!TextUtils.isEmpty(dto.getTransType())){
                    jsonObject.put("transType",dto.getTransType());
                }
                if(!TextUtils.isEmpty(dto.getSortType())){
                    jsonObject.put("sortType",dto.getSortType());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.getTradeDelation(body).enqueue(new CallBack<TradeDelationDto>() {
            @Override
            public void success(TradeDelationDto response) {
                view.isLastPage(response.isLastPage());
                view.dissLoading();
                view.getTradeList(response);
                Log.e("msg","是否最后一页"+response.isLastPage());

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

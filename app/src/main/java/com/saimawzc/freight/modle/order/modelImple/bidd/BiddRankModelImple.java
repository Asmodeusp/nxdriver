package com.saimawzc.freight.modle.order.modelImple.bidd;


import com.saimawzc.freight.dto.order.waybill.RankPageDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.bidd.RankBiddModel;
import com.saimawzc.freight.view.order.BiddRandView;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 竞价
 * **/
public class BiddRankModelImple extends BasEModeImple implements RankBiddModel {



    @Override
    public void getRankLsit(final BiddRandView view, int page, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("pageNum",page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getRankList(body).enqueue(new CallBack<RankPageDto>() {
            @Override
            public void success(RankPageDto response) {
                view.dissLoading();
                view.isLastPage(response.isLastPage());
                view.getRandLise(response.getList());
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}

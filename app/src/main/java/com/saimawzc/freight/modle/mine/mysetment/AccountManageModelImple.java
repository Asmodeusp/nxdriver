package com.saimawzc.freight.modle.mine.mysetment;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.SearchValueDto;
import com.saimawzc.freight.dto.account.AccountQueryPageDto;
import com.saimawzc.freight.dto.account.ReconclitionDto;
import com.saimawzc.freight.dto.my.lessess.LessessPageDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.mine.lessess.MyLessessModel;
import com.saimawzc.freight.view.mine.lessess.MyLessessView;
import com.saimawzc.freight.view.mine.setment.AccountManageView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.lessess.MyLessessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/10.
 * 我的司机
 */

public class AccountManageModelImple extends BasEModeImple implements MyAccountManageModel {

    @Override
    public void datas(int page, List<SearchValueDto>searchdtos , final AccountManageView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            jsonObject.put("pageSize",20);
            if(searchdtos!=null){
                for(int i=0;i<searchdtos.size();i++){
                    jsonObject.put(searchdtos.get(i).getSearchName(),searchdtos.get(i).getGetSearchValue());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.getAccountList(body).enqueue(new CallBack<AccountQueryPageDto>() {
            @Override
            public void success(AccountQueryPageDto response) {
                view.dissLoading();
                view.getData(response);
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

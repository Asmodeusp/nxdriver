package com.saimawzc.freight.presenter.order;

import android.content.Context;

import com.saimawzc.freight.dto.order.SendCarDto;
import com.saimawzc.freight.modle.order.modelImple.SendCarLsitModelImple;
import com.saimawzc.freight.modle.order.modle.SendCarLsitModel;
import com.saimawzc.freight.view.order.SendCarListView;
import com.saimawzc.freight.weight.utils.listen.order.SendCarListListener;

import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */

public class SendCarLsitPresenter implements SendCarListListener {

    private Context mContext;
    SendCarListView view;
    SendCarLsitModel model;

    public SendCarLsitPresenter(SendCarListView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new SendCarLsitModelImple() ;
    }
    public void getSendCarList(int page,String status,String seatchType,String searchValue){
        model.getSendCarLsit(view,this,page,status,seatchType,searchValue);
        }


    @Override
    public void successful() {
        view.oncomplete();
    }
    @Override
    public void onFail(String str) {
        view.Toast(str);
    }

    @Override
    public void successful(int type) {


    }


    @Override
    public void getManageOrderList(List<SendCarDto.SendCarData> dtos) {
        view.getSendCarList(dtos);

    }
}

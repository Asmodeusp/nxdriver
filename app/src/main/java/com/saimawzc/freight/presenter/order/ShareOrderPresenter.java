package com.saimawzc.freight.presenter.order;

import android.content.Context;

import com.saimawzc.freight.dto.order.mainindex.WaitOrderDto;
import com.saimawzc.freight.modle.order.modelImple.ShareOrderModelImple;
import com.saimawzc.freight.modle.order.modelImple.WaitOrderModelImple;
import com.saimawzc.freight.modle.order.modle.WaitOrderModel;
import com.saimawzc.freight.view.order.WaitOrderView;
import com.saimawzc.freight.weight.utils.listen.order.WaitOrderListener;

import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */

public class ShareOrderPresenter implements WaitOrderListener {

    private Context mContext;
    WaitOrderView view;
    WaitOrderModel model;
    public ShareOrderPresenter(WaitOrderView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ShareOrderModelImple() ;
    }

    public void getData(int page){
        model.getCarList(view,this,page);
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
    public void waitordetList(List<WaitOrderDto.waitOrderData> dtos) {
        view.getPlanOrderList(dtos);
    }
}

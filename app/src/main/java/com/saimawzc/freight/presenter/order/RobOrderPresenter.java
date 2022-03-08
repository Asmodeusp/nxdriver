package com.saimawzc.freight.presenter.order;

import android.content.Context;

import com.saimawzc.freight.dto.order.ManageListDto;
import com.saimawzc.freight.dto.order.mainindex.RobOrderDto;
import com.saimawzc.freight.modle.order.modelImple.ManageOrderModelImple;
import com.saimawzc.freight.modle.order.modelImple.RobOrderModelImple;
import com.saimawzc.freight.modle.order.modle.ManageOrderModel;
import com.saimawzc.freight.modle.order.modle.RobOrderModel;
import com.saimawzc.freight.view.order.ManageOrderView;
import com.saimawzc.freight.view.order.RobOrderView;
import com.saimawzc.freight.weight.utils.listen.order.ManageOrderListener;
import com.saimawzc.freight.weight.utils.listen.order.RobOrderListener;

import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 * 抢单
 */

public class RobOrderPresenter implements RobOrderListener {

    private Context mContext;
    RobOrderView view;
    RobOrderModel model;
    public RobOrderPresenter(RobOrderView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new RobOrderModelImple() ;
    }

    public void getData(int page){
        model.getRobLsit(view,this,page);
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
    public void getManageOrderList(List<RobOrderDto.robOrderData> dtos) {
        view.getPlanOrderList(dtos);
    }
}

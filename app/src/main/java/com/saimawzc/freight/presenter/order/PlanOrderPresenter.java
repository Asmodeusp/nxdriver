package com.saimawzc.freight.presenter.order;

import android.content.Context;

import com.saimawzc.freight.dto.order.bill.MyPlanOrderDto;
import com.saimawzc.freight.modle.order.modelImple.PlanOrderModelImple;
import com.saimawzc.freight.modle.order.modle.PlanOrderModel;
import com.saimawzc.freight.view.order.PlanOrderView;
import com.saimawzc.freight.weight.utils.listen.order.PlanOrderListener;

import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */

public class PlanOrderPresenter implements PlanOrderListener {

    private Context mContext;
    PlanOrderView view;
    PlanOrderModel model;
    public PlanOrderPresenter(PlanOrderView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new PlanOrderModelImple() ;
    }

    public void getData(int page,String searchType,String searchValue,int waybillstatus){
        model.getCarList(view,this,page,searchType,searchValue,waybillstatus);
    }
    public void getsjData(int page,String searchType,String searchValue){
        model.getsjCarList(view,this,page,searchType,searchValue);
    }
    public void reshItem(String id,int position){
        model.reshData(view,position,id);
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
    public void planOrderList(List<MyPlanOrderDto.planOrderData> dtos) {
        view.getPlanOrderList(dtos);
    }
}

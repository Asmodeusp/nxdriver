package com.saimawzc.freight.presenter.order;

import android.content.Context;

import com.saimawzc.freight.dto.order.bill.MyPlanOrderDto;
import com.saimawzc.freight.dto.order.bill.WayBillDto;
import com.saimawzc.freight.modle.order.modelImple.PlanOrderModelImple;
import com.saimawzc.freight.modle.order.modelImple.WayBillModelImple;
import com.saimawzc.freight.modle.order.modle.PlanOrderModel;
import com.saimawzc.freight.modle.order.modle.WayBillModel;
import com.saimawzc.freight.view.order.PlanOrderView;
import com.saimawzc.freight.view.order.WayBillView;
import com.saimawzc.freight.weight.utils.listen.order.PlanOrderListener;
import com.saimawzc.freight.weight.utils.listen.order.WayBillListener;

import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */

public class WayBillPresenter implements WayBillListener {

    private Context mContext;
    WayBillModel model;
    WayBillView view;
    public WayBillPresenter(WayBillView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new WayBillModelImple() ;
    }

    public void getData(int page,String searchType,String searchValue){
        model.getWayBill(view,this,page,searchType,searchValue);
    }
    public void delete(String id){
        model.delete(view,this,id);
    }

    public void getsjData(int page,String searchType,String searchValue){
        model.getsjWayBill(view,this,page,searchType,searchValue);
    }
    public void sjdelete(String id){
        model.sjdelete(view,this,id);
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
    public void planOrderList(List<WayBillDto.OrderBillData> dtos) {
        view.getPlanOrderList(dtos);
    }
}

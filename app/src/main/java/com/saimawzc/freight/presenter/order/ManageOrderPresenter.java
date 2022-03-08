package com.saimawzc.freight.presenter.order;

import android.content.Context;

import com.saimawzc.freight.dto.order.ManageListDto;
import com.saimawzc.freight.modle.order.modelImple.ManageOrderModelImple;
import com.saimawzc.freight.modle.order.modle.ManageOrderModel;
import com.saimawzc.freight.view.order.ManageOrderView;
import com.saimawzc.freight.weight.utils.listen.order.ManageOrderListener;


import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */

public class ManageOrderPresenter implements ManageOrderListener {

    private Context mContext;
    ManageOrderView view;
    ManageOrderModel model;
    public ManageOrderPresenter(ManageOrderView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ManageOrderModelImple() ;
    }

    public void getData(int page,String searchType,String searchValue){
        model.getCarList(view,this,page,searchType,searchValue);
    }

    public void delete(String id){
        model.delete(view,this,id);
    }

    public void sjgetData(int page,String searchType,String searchValue){
        model.getsjCarList(view,this,page,searchType,searchValue);
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
    public void getManageOrderList(List<ManageListDto.ManageOrderData> dtos) {
        view.getPlanOrderList(dtos);
    }
}

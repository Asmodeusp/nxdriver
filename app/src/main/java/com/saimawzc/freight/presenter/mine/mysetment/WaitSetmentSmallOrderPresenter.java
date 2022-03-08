package com.saimawzc.freight.presenter.mine.mysetment;

import android.content.Context;

import com.saimawzc.freight.modle.mine.mysetment.WaitSetmentSmallOrderModel;
import com.saimawzc.freight.modle.mine.mysetment.WaitSetmentSmallOrderModelImple;
import com.saimawzc.freight.view.mine.setment.WaitSetmentSmallOrderView;

import java.util.List;


/**
 * Created by Administrator on 2020/8/3.
 */

public class WaitSetmentSmallOrderPresenter {
    private Context mContext;
    WaitSetmentSmallOrderModel model;
    WaitSetmentSmallOrderView view;

    public WaitSetmentSmallOrderPresenter(WaitSetmentSmallOrderView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new WaitSetmentSmallOrderModelImple();
    }
    public void getData(int page,String id){
        model.getList(page,id,view);
    }

    public void addSetmenet(List<String>dtos){
        model.addsetment(dtos,view);
    }


}

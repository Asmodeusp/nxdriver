package com.saimawzc.freight.presenter.mine.mysetment;

import android.content.Context;


import com.saimawzc.freight.dto.account.ConsignmentCompanyDto;
import com.saimawzc.freight.modle.mine.mysetment.ConsignCompanyModelImple;
import com.saimawzc.freight.modle.mine.mysetment.ConsignSignCompanyModel;
import com.saimawzc.freight.view.mine.setment.ConsignCompanyView;
import com.saimawzc.freight.weight.utils.listen.order.ConsignCompanyListener;

import java.util.List;

public class ConsignCompanyPresenter implements ConsignCompanyListener {

    private Context mContext;
    ConsignSignCompanyModel model;
    ConsignCompanyView view;

    public ConsignCompanyPresenter(ConsignCompanyView useView, Context context) {
        this.view = useView;
        this.mContext = context;
        model=new ConsignCompanyModelImple();
    }
    public void getConsinList(){
        model.getCompanyList(view,this);
    }


    @Override
    public void successful() {
    }

    @Override
    public void onFail(String str) {
    view.Toast(str);
    }
    @Override
    public void successful(int type) {
    }
    @Override
    public void back(List<ConsignmentCompanyDto> dtos) {
        view.getCompany(dtos);

    }
}

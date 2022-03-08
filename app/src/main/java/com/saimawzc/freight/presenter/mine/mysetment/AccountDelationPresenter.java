package com.saimawzc.freight.presenter.mine.mysetment;

import android.content.Context;

import com.saimawzc.freight.dto.SearchValueDto;
import com.saimawzc.freight.modle.mine.mysetment.AccountDelationModel;
import com.saimawzc.freight.modle.mine.mysetment.AccountDelationModelImple;
import com.saimawzc.freight.modle.mine.mysetment.AccountManageModelImple;
import com.saimawzc.freight.modle.mine.mysetment.MyAccountManageModel;
import com.saimawzc.freight.view.mine.setment.AccountDelationView;
import com.saimawzc.freight.view.mine.setment.AccountManageView;

import java.util.List;

/**
 * Created by Administrator on 2020/8/10.
 */

public class AccountDelationPresenter {
    private Context mContext;
    AccountDelationModel model;
    AccountDelationView view;
    public AccountDelationPresenter(AccountDelationView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new AccountDelationModelImple();
    }

    public void getData(String id){
        model.datas(id,view);
    }



}

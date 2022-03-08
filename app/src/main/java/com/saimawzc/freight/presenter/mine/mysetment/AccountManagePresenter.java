package com.saimawzc.freight.presenter.mine.mysetment;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.SearchValueDto;
import com.saimawzc.freight.dto.my.lessess.LessessPageDto;
import com.saimawzc.freight.modle.mine.lessess.MyLessessModel;
import com.saimawzc.freight.modle.mine.lessess.MyLessessModelImple;
import com.saimawzc.freight.modle.mine.mysetment.AccountManageModelImple;
import com.saimawzc.freight.modle.mine.mysetment.MyAccountManageModel;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.view.mine.lessess.MyLessessView;
import com.saimawzc.freight.view.mine.setment.AccountManageView;
import com.saimawzc.freight.weight.utils.listen.lessess.MyLessessListener;

import java.util.List;

/**
 * Created by Administrator on 2020/8/10.
 */

public class AccountManagePresenter  {
    private Context mContext;
    MyAccountManageModel model;
    AccountManageView view;
    public AccountManagePresenter(AccountManageView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new AccountManageModelImple();
    }

    public void getData(int page, List<SearchValueDto>list){
        model.datas(page,list,view);
    }



}

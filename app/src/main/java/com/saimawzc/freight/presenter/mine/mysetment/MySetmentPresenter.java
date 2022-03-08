package com.saimawzc.freight.presenter.mine.mysetment;

import android.content.Context;

import com.saimawzc.freight.dto.SearchValueDto;
import com.saimawzc.freight.modle.mine.mysetment.MySetmentModel;
import com.saimawzc.freight.modle.mine.mysetment.MySetmentModelImple;
import com.saimawzc.freight.view.mine.setment.MySetmentView;

import java.util.List;


/**
 * Created by Administrator on 2020/8/3.
 */

public class MySetmentPresenter {
    private Context mContext;
    MySetmentModel model;
    MySetmentView view;

    public MySetmentPresenter(MySetmentView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MySetmentModelImple();
    }
    public void getData(int page, int type,List<SearchValueDto>dtos){
        model.getList(page,type,dtos,view);
    }

    public void confirm(int type,String id){
        model.comfirm(type,id,view);
    }


}

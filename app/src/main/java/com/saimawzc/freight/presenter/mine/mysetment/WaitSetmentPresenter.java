package com.saimawzc.freight.presenter.mine.mysetment;

import android.content.Context;

import com.saimawzc.freight.modle.mine.mysetment.MySetmentModel;
import com.saimawzc.freight.modle.mine.mysetment.MySetmentModelImple;
import com.saimawzc.freight.modle.mine.mysetment.WaitSetmentModel;
import com.saimawzc.freight.modle.mine.mysetment.WaitSetmentModelImple;
import com.saimawzc.freight.view.mine.setment.MySetmentView;
import com.saimawzc.freight.view.mine.setment.WaitSetmentView;


/**
 * Created by Administrator on 2020/8/3.
 */

public class WaitSetmentPresenter {
    private Context mContext;
    WaitSetmentModel model;
    WaitSetmentView view;

    public WaitSetmentPresenter(WaitSetmentView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new WaitSetmentModelImple();
    }
    public void getData(int page){
        model.getList(page,view);
    }


}

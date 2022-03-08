package com.saimawzc.freight.presenter.order;

import android.content.Context;

import com.saimawzc.freight.modle.order.modelImple.MyErrorModelImple;
import com.saimawzc.freight.modle.order.modle.bidd.MyErrorModel;
import com.saimawzc.freight.view.order.error.MyErrorView;

/**
 * Created by Administrator on 2020/7/30.
 */

public class MyErrorReportPresenter {

    private Context mContext;
    MyErrorView view;
    MyErrorModel model;
    public MyErrorReportPresenter(MyErrorView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MyErrorModelImple() ;
    }

    public void getErrorType(String id){
        model.getErrorType(view,id);
    }

}

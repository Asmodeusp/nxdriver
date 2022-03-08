package com.saimawzc.freight.presenter.order;

import android.content.Context;
import com.saimawzc.freight.modle.order.modelImple.ExamGoodModelImple;
import com.saimawzc.freight.modle.order.modle.ExamGoodModel;
import com.saimawzc.freight.view.order.ExamGoodView;

/**
 * Created by Administrator on 2020/7/30.
 */

public class ExamGoodPresenter  {

    private Context mContext;
    ExamGoodView view;
    ExamGoodModel model;
    public ExamGoodPresenter(ExamGoodView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ExamGoodModelImple() ;
    }

    public void getExamGoodList(String id){
        model.getExamGood(view,id);

    }


}

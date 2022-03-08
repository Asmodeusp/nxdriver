package com.saimawzc.freight.presenter.mine.carleader;

import android.content.Context;
import com.saimawzc.freight.modle.mine.carleader.CarQueueModel;
import com.saimawzc.freight.modle.mine.carleader.imple.CarQueueModelImple;
import com.saimawzc.freight.view.mine.queue.MyQueueView;

/**
 * Created by Administrator on 2020/8/10.
 */

public class CarQueuePresenter {
    private Context mContext;
    CarQueueModel model;
    MyQueueView view;


    public CarQueuePresenter(MyQueueView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new CarQueueModelImple();
    }

    public void getData(int page,int status){
        model.getData(view,page,status);

    }



}

package com.saimawzc.freight.presenter.order;

import android.content.Context;
import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.modle.order.modelImple.StopTrantModelImple;
import com.saimawzc.freight.modle.order.modle.bidd.StopTrantModel;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 * 申请停运
 */

public class StopTrantPresenter implements BaseListener {

    private Context mContext;
    BaseView view;
    StopTrantModel model;
    public StopTrantPresenter(BaseView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new StopTrantModelImple() ;
    }

    public void stopTrant(String id,String reason){
        model.stopTrant(view,this,id,reason);
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

}

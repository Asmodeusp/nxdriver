package com.saimawzc.freight.presenter.mine.carrier;

import android.content.Context;

import com.saimawzc.freight.dto.my.carrier.CarrierPageDto;
import com.saimawzc.freight.modle.mine.carrier.MyCarrierModel;
import com.saimawzc.freight.modle.mine.carrier.MyCarrierModelImple;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.view.mine.carrier.MyCarrierView;
import com.saimawzc.freight.weight.utils.listen.carrier.MyCarrierListener;
import java.util.List;

/**
 * Created by Administrator on 2020/8/10.
 */

public class MyCarrierPresenter implements BaseView,MyCarrierListener {
    private Context mContext;
    MyCarrierModel model;
    MyCarrierView view;


    public MyCarrierPresenter(MyCarrierView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MyCarrierModelImple();

    }
    public void getcarList(int type,int page,String carNO){
        model.getCarrier(view,this,type,page,carNO);

    }





    @Override
    public void showLoading() {
        view.showLoading();

    }

    @Override
    public void dissLoading() {
        view.dissLoading();
    }

    @Override
    public void Toast(String str) {
        view.Toast(str);
    }

    @Override
    public void oncomplete() {

    }




    @Override
    public void callbackbrand(CarrierPageDto myDriverDtos) {
        view.getMyCarrierList(myDriverDtos);
    }
}

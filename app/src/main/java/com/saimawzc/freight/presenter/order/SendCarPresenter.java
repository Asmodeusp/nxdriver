package com.saimawzc.freight.presenter.order;

import android.content.Context;
import com.saimawzc.freight.dto.order.CarInfolDto;
import com.saimawzc.freight.dto.order.CarModelDto;
import com.saimawzc.freight.modle.order.modelImple.SendCarModelImple;
import com.saimawzc.freight.modle.order.modle.SendCarModel;
import com.saimawzc.freight.view.order.SendCarView;
import com.saimawzc.freight.weight.utils.listen.order.SendCarInfolListen;
import com.saimawzc.freight.weight.utils.listen.order.SendCarModellListen;

import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */

public class SendCarPresenter implements SendCarModellListen, SendCarInfolListen {

    private Context mContext;
    SendCarView view;
    SendCarModel model;
    public SendCarPresenter(SendCarView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new SendCarModelImple() ;
    }

    public void getCarmodel(int trantType){
        model.getCarModel(view,this,trantType);
    }

    public void getCarInfo(int page,String carTypeId,String search,int trantType,String companyId){
        model.getCarInfo(view,this,page,carTypeId,search,trantType,companyId);
    }
    public void getsjCarInfo(int page,String carTypeId,String search,int trantType,String companyId){
        model.getsjCarInfo(view,this,page,carTypeId,search,trantType,companyId);
    }
    public void sendCar(CarInfolDto.carInfoData carInfo,
                        String type, String biLLid, String sendCarNum){
        model.sendCar(view,carInfo,type,biLLid,sendCarNum);
    }

    public void isHavaBeiDou(String type,String id,String carId,String carNo){
        model.carIsHasBeiDou(view,type,id,carId,carNo);
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



    @Override
    public void back(List<CarModelDto> dtos) {
        view.getCarModelList(dtos);
    }

    @Override
    public void backinfo(List<CarInfolDto.carInfoData> dtos) {
        view.getCarInfolList(dtos);

    }
}

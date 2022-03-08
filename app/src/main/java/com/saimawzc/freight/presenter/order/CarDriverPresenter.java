package com.saimawzc.freight.presenter.order;

import android.content.Context;
import com.saimawzc.freight.dto.order.CarDriverDto;
import com.saimawzc.freight.dto.order.CarInfolDto;
import com.saimawzc.freight.modle.order.modelImple.CarDriverModelImple;
import com.saimawzc.freight.modle.order.modle.CarDriverModel;
import com.saimawzc.freight.view.order.SendDriverView;
import com.saimawzc.freight.weight.utils.listen.order.CarDriverListener;
import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */

public class CarDriverPresenter implements CarDriverListener {

    private Context mContext;
    SendDriverView view;
    CarDriverModel model;
    public CarDriverPresenter(SendDriverView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new CarDriverModelImple() ;
    }

    public void getData(String phone,int trantType,String carId, String companyId){
        model.getCarDriverList(view,this,phone,trantType,carId,companyId);
    }
    public void getDatabyPage(String phone,int trantType,String carId,int page,String companyId){
        model.getDriverListByPage(view,this,phone,trantType,carId,page,companyId);
    }

    public void sendCar(CarInfolDto.carInfoData carInfo,CarDriverDto driverDto,
                        String type,String biLLid,String sendCarNum){
        model.sendCar(view,this,carInfo,driverDto,type,biLLid,sendCarNum);
    }
    public void sendsjCar(CarInfolDto.carInfoData carInfo,CarDriverDto driverDto,
                        String type,String biLLid,String sendCarNum){
        model.sendsjCar(view,this,carInfo,driverDto,type,biLLid,sendCarNum);
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
    public void getManageOrderList(List<CarDriverDto> dtos) {
        view.getDriverList(dtos);
    }
}

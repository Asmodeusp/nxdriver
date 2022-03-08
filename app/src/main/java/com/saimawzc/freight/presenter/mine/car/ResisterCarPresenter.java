package com.saimawzc.freight.presenter.mine.car;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.my.CarBrandDto;
import com.saimawzc.freight.dto.my.CarTypeDo;
import com.saimawzc.freight.dto.my.car.CarIsRegsister;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.modle.mine.car.ResisterCarModel;
import com.saimawzc.freight.modle.mine.car.ResisterCarModelImple;
import com.saimawzc.freight.view.mine.car.ResisterCarView;
import com.saimawzc.freight.view.mine.identificaion.DriveLicesenCarrierView;
import com.saimawzc.freight.weight.utils.WheelDialog;
import com.saimawzc.freight.weight.utils.listen.WheelListener;
import com.saimawzc.freight.weight.utils.listen.car.CarBrandListener;
import com.saimawzc.freight.weight.utils.listen.car.CarTypeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/8/3.
 */

public class ResisterCarPresenter implements BaseListener,CarTypeListener,CarBrandListener{
    private Context mContext;
    ResisterCarModel model;
    ResisterCarView view;
    private WheelDialog wheelDialog;

    public ResisterCarPresenter(ResisterCarView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ResisterCarModelImple();

    }

    public void showCamera( Context context,  int type){//type 0是身份证正面  1是身份证反面

        model.showCamera(context, type,this) ;
    }

    /**获取车辆详情***/
    public void getCarInfo(String id){
      model.getCarInfo(view,this,id);
    }


    public  void carriveRz(int isConsistent){
        model.identification(view,this,isConsistent);
    }
    /***
     * 修改车辆
     * **/
    public  void updateCarInfo(String id,int isConsistent){
        model.modifyCar(view,this,id,isConsistent);
    }


    public  void dissCamera(){
        model.dissCamera();
    }
    public void getCarType(){
        model.getCarType(view,this);
    }
    public void getCarBrand(String carType){
        model.getBrand(view,this,carType);
    }

    /*被车辆是否被注册*
     *
     * **/
    public void isResister(String carNum){
      model.isregster(view,this,carNum);
    }

    public void getTrafficDto(String carNo,int carcolor){
        model.ZhiYunCarInfo(view,carNo,carcolor);
    }


    @Override
    public void successful() {//完成认证
        view.Toast("提交成功");
        view.oncomplete();

    }
    @Override
    public void onFail(String str) {
        view.Toast(str);
    }

    @Override
    public void successful(int type) {
        view.OnDealCamera(type);
    }


    @Override
    public void callbacktype(List<CarTypeDo> carTypeDos) {

        if(carTypeDos.size()<=0){
            view.Toast("该车型没有相关品牌");
            return;
        }
        List<String> strings=new ArrayList<>();
        for(int i=0;i<carTypeDos.size();i++){
            strings.add(carTypeDos.get(i).getCarTypeName());
        }

        wheelDialog=new WheelDialog(mContext,carTypeDos,strings);
        wheelDialog.Show(new WheelListener() {
            @Override
            public void callback(String name, String id) {
                view.carTypeName(name);
                view.carTypeId(id);
            }
        });
    }
   //搜索车辆
    @Override
    public void carinfoListen(SearchCarDto dto) {
        view.getCarInfo(dto);
    }


    @Override
    public void isRegsister(CarIsRegsister dto) {
        view.isResister(dto);
    }



    @Override
    public void callbackbrand(List<CarBrandDto> carTypeDos) {
        List<String> strings=new ArrayList<>();
        for(int i=0;i<carTypeDos.size();i++){
            strings.add(carTypeDos.get(i).getBrandName());
        }
        wheelDialog=new WheelDialog(mContext,carTypeDos,strings);
        wheelDialog.Show(new WheelListener() {
            @Override
            public void callback(String name, String id) {
                view.carBrandName(name);
                view.carBrandid(id);
            }
        });
    }
}

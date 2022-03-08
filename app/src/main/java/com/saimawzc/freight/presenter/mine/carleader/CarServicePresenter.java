package com.saimawzc.freight.presenter.mine.carleader;

import android.content.Context;

import com.saimawzc.freight.dto.my.CarBrandDto;
import com.saimawzc.freight.dto.my.CarTypeDo;
import com.saimawzc.freight.dto.my.car.CarIsRegsister;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.modle.mine.carleader.CarServiceModel;
import com.saimawzc.freight.modle.mine.carleader.imple.CarServiceModelImple;
import com.saimawzc.freight.view.mine.carleader.CarServiceView;
import com.saimawzc.freight.weight.utils.WheelDialog;
import com.saimawzc.freight.weight.utils.listen.WheelListener;
import com.saimawzc.freight.weight.utils.listen.car.CarBrandListener;
import com.saimawzc.freight.weight.utils.listen.car.CarTypeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */

public class CarServicePresenter implements CarTypeListener,
        CarBrandListener {

    private Context mContext;
    CarServiceView view;
    CarServiceModel model;
    public CarServicePresenter(CarServiceView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new CarServiceModelImple() ;
    }

    public void submit(){
        model.edit(view);
    }

    public void showCamera(Context context,int type){
        model.showCamera(view,context,type) ;
    }

    public void getCarType(){
        model.getCarType(view,this);
    }
    public void getCarBrand(String carType){
        model.getBrand(view,this,carType);
    }

    public void getSfinfo(String carId,String cdzId,String sjId){
        model.getSfInfo(view,carId,cdzId,sjId);

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
    private WheelDialog wheelDialog;
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

    @Override
    public void carinfoListen(SearchCarDto dto) {

    }

    @Override
    public void isRegsister(CarIsRegsister dto) {

    }
}

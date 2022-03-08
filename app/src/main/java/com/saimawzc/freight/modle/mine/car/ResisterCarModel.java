package com.saimawzc.freight.modle.mine.car;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.mine.car.ResisterCarView;
import com.saimawzc.freight.view.mine.identificaion.DriveLicesenCarrierView;
import com.saimawzc.freight.weight.utils.listen.WheelListener;
import com.saimawzc.freight.weight.utils.listen.car.CarBrandListener;
import com.saimawzc.freight.weight.utils.listen.car.CarTypeListener;
import com.saimawzc.freight.weight.utils.listen.car.SearchCarListener;

/**
 * Created by Administrator on 2020/8/6.
 */

public interface ResisterCarModel  {

    void identification(ResisterCarView view, BaseListener listener,int isConsistent);
    void showCamera(Context context, int type, BaseListener listener);
    void dissCamera();
    void getCarType( ResisterCarView view,CarTypeListener listener);
    void getBrand( ResisterCarView view,CarBrandListener listener,String type);
    void getCarInfo(ResisterCarView view, CarTypeListener listener,String id);
    void isregster(ResisterCarView view, CarTypeListener listener,String carNum);

    void modifyCar(ResisterCarView view, BaseListener listener,String id,int isConsistent);

    void ZhiYunCarInfo(ResisterCarView view,String carNo,int colorType);
}

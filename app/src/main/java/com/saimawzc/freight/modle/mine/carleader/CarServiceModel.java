package com.saimawzc.freight.modle.mine.carleader;


import android.content.Context;

import com.saimawzc.freight.view.mine.carleader.CarServiceView;

import com.saimawzc.freight.weight.utils.listen.car.CarBrandListener;
import com.saimawzc.freight.weight.utils.listen.car.CarTypeListener;
/**
 * Created by Administrator on 2020/7/31.
 */

public interface CarServiceModel {


   void edit(CarServiceView view);
   void showCamera(CarServiceView view, Context context, int type);
   void getCarType(CarServiceView view, CarTypeListener listener);
   void getBrand(CarServiceView view, CarBrandListener listener, String type);
   void getSfInfo(CarServiceView view, String carId,String cdzId, String sjId);

}

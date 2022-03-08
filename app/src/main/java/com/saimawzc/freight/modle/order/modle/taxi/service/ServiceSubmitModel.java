package com.saimawzc.freight.modle.order.modle.taxi.service;


import android.content.Context;

import com.saimawzc.freight.view.face.TaxaSysView;
import com.saimawzc.freight.view.order.taxi.service.ServiceSubmitView;
import com.saimawzc.freight.weight.utils.listen.car.CarBrandListener;
import com.saimawzc.freight.weight.utils.listen.car.CarTypeListener;

/**
 * Created by Administrator on 2020/7/31.
 */

public interface ServiceSubmitModel {


   void edit(ServiceSubmitView view);
   void showCamera(ServiceSubmitView view, Context context, int type);
   void getCarType(ServiceSubmitView view, CarTypeListener listener);
   void getBrand(ServiceSubmitView view, CarBrandListener listener, String type);
   void getSfInfo(ServiceSubmitView view,String roleId);
   void getCarInfo(ServiceSubmitView view,String carNO);

}

package com.saimawzc.freight.modle.face;


import android.content.Context;
import com.saimawzc.freight.view.face.TaxaSysView;
import com.saimawzc.freight.weight.utils.listen.car.CarBrandListener;
import com.saimawzc.freight.weight.utils.listen.car.CarTypeListener;

/**
 * Created by Administrator on 2020/7/31.
 */

public interface TaxaSysModel {


   void edit(TaxaSysView view);
   void showCamera(TaxaSysView view, Context context,int type);
   void getCarType(TaxaSysView view, CarTypeListener listener);
   void getBrand(TaxaSysView view, CarBrandListener listener, String type);
}

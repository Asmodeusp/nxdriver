package com.saimawzc.freight.modle.mine.identification;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.view.mine.identificaion.NomalTaxesCarriverView;
import com.saimawzc.freight.view.mine.identificaion.PersonCarrierView;
import com.saimawzc.freight.weight.utils.listen.identification.CarriverIdentificationListener;

/**
 * Created by Administrator on 2020/8/3.
 */

public interface PersonCarrierModel {
    void identification(PersonCarrierView view, BaseListener listener);
    void reidentification(PersonCarrierView view, BaseListener listener);
    void showCamera(Context context,int type,BaseListener listener);
    void dissCamera();
    void getArea(BaseListener listener);
    void getIdentificationInfo(PersonCarrierView view,CarriverIdentificationListener listener);
}

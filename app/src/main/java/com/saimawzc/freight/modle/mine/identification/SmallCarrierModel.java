package com.saimawzc.freight.modle.mine.identification;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.view.mine.identificaion.NomalTaxesCarriverView;
import com.saimawzc.freight.view.mine.identificaion.SmallCompanyCarrierView;
import com.saimawzc.freight.weight.utils.listen.identification.CarriverIdentificationListener;

/**
 * Created by Administrator on 2020/8/3.
 * 一般纳税人Model
 */

public interface SmallCarrierModel {

    void identification(SmallCompanyCarrierView view, BaseListener listener);
    void reidentification(SmallCompanyCarrierView view, BaseListener listener);
    void showCamera(Context context, int type, BaseListener listener);
    void dissCamera();
    void getIdentificationInfo(SmallCompanyCarrierView view,CarriverIdentificationListener listener);

}

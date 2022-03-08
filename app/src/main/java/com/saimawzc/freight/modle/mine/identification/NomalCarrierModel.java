package com.saimawzc.freight.modle.mine.identification;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.view.mine.identificaion.NomalTaxesCarriverView;
import com.saimawzc.freight.view.mine.identificaion.PersonCarrierView;
import com.saimawzc.freight.weight.utils.listen.identification.CarriverIdentificationListener;

/**
 * Created by Administrator on 2020/8/3.
 * 一般纳税人Model
 */

public interface NomalCarrierModel {

    void identification(NomalTaxesCarriverView view, BaseListener listener);
    void reidentification(NomalTaxesCarriverView view, BaseListener listener);
    void showCamera(Context context, int type, BaseListener listener);
    void dissCamera();
    void getIdentificationInfo(NomalTaxesCarriverView view,CarriverIdentificationListener listener);

}

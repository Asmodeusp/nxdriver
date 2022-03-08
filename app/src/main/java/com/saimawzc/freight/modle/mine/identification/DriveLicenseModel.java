package com.saimawzc.freight.modle.mine.identification;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.view.mine.identificaion.DriveLicesenCarrierView;
import com.saimawzc.freight.weight.utils.listen.identification.DriviceIdentificationListener;

/**
 * Created by Administrator on 2020/8/4.
 * 司机认证
 */

public interface DriveLicenseModel {
    void identification(DriveLicesenCarrierView view, BaseListener listener,int isConsistent);
    void reidentification(DriveLicesenCarrierView view, BaseListener listener,int isConsistent);
    void showCamera(Context context, int type, BaseListener listener);

    void dissCamera();

    void getIdentificationInfo(DriveLicesenCarrierView view,DriviceIdentificationListener listener);
}

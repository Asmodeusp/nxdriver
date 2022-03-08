package com.saimawzc.freight.modle.mine.car.ship;

import android.content.Context;

import com.saimawzc.freight.view.mine.car.ship.ResisterShipView;

/**
 * Created by Administrator on 2020/8/6.
 */

public interface ResisterShipModel {

    void identification(ResisterShipView view);
    void showCamera(Context context, int type, ResisterShipView view);
    void dissCamera();
    void getShipType(ResisterShipView view);
    void getShipInfo(ResisterShipView view, String id);
    void isregster(ResisterShipView view, String shipId);
    void ismodify(ResisterShipView view, String shipId);
}

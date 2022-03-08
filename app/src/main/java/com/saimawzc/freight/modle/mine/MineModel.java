package com.saimawzc.freight.modle.mine;

import com.saimawzc.freight.view.mine.MineView;

/**
 * Created by Administrator on 2020/8/1.
 */

public interface MineModel {

    void getPerson(MineView view);
    void getCarrierList(MineView view);
    void getLessessList(MineView view);
    void getSonAccount(MineView view);
}

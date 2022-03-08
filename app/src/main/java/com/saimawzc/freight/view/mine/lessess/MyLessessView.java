package com.saimawzc.freight.view.mine.lessess;

import com.saimawzc.freight.dto.my.lessess.LessessPageDto;
import com.saimawzc.freight.dto.my.lessess.MyLessessDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2020/8/10.
 */

public interface MyLessessView extends BaseView {
    void getLessessList(LessessPageDto lessessDtos);
    void stopRefresh();
}

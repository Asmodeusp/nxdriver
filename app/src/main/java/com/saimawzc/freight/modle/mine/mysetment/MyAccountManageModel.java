package com.saimawzc.freight.modle.mine.mysetment;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.SearchValueDto;
import com.saimawzc.freight.view.mine.lessess.MyLessessView;
import com.saimawzc.freight.view.mine.setment.AccountManageView;
import com.saimawzc.freight.weight.utils.listen.lessess.MyLessessListener;

import java.util.List;

/**
 * Created by Administrator on 2020/8/10.
 * 对账管理
 */

public interface MyAccountManageModel {
    void datas(int page , List<SearchValueDto> searchValueDtos, AccountManageView view);
}

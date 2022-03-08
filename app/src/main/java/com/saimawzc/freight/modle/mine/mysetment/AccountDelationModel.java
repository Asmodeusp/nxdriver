package com.saimawzc.freight.modle.mine.mysetment;

import com.saimawzc.freight.dto.SearchValueDto;
import com.saimawzc.freight.view.mine.setment.AccountDelationView;
import com.saimawzc.freight.view.mine.setment.AccountManageView;

import java.util.List;

/**
 * Created by Administrator on 2020/8/10.
 * 对账管理
 */

public interface AccountDelationModel {
    void datas(String id, AccountDelationView view);
}

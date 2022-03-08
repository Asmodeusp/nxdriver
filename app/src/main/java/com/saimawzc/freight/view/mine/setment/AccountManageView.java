package com.saimawzc.freight.view.mine.setment;

import com.saimawzc.freight.dto.account.AccountQueryPageDto;
import com.saimawzc.freight.dto.account.ReconclitionDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface AccountManageView extends BaseView {
    void getData(AccountQueryPageDto dtos);
    void stopRefresh();

}

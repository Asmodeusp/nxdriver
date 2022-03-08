package com.saimawzc.freight.view.wallet;

import com.saimawzc.freight.dto.wallet.MsBankDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface MsBankView extends BaseView {

    void getBinkList(List<MsBankDto>dtos);

    void getBigBank(List<MsBankDto>dtos);
}

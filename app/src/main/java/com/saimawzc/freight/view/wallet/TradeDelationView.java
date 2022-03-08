package com.saimawzc.freight.view.wallet;

import com.saimawzc.freight.dto.wallet.TradeDelationDto;
import com.saimawzc.freight.view.BaseView;

public interface TradeDelationView extends BaseView {

    void getTradeList(TradeDelationDto delationDto);
    void isLastPage(boolean isLastPage);

}

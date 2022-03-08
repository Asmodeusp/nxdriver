package com.saimawzc.freight.modle.wallet;
import com.saimawzc.freight.dto.wallet.TradeChooseDto;
import com.saimawzc.freight.view.wallet.TradeDelationView;

public interface TradeDealtionModel {

    void getTradeList(TradeDelationView view, int page,TradeChooseDto dto);


}

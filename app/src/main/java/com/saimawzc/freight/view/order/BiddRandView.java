package com.saimawzc.freight.view.order;



import com.saimawzc.freight.dto.order.waybill.RankPageDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface BiddRandView extends BaseView {


    void getRandLise(List<RankPageDto.rankDto> dtos);
    void  isLastPage(boolean isLastPage);

}

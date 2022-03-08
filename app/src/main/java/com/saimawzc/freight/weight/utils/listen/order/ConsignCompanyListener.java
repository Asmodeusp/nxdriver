package com.saimawzc.freight.weight.utils.listen.order;



import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.account.ConsignmentCompanyDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 */

public interface ConsignCompanyListener extends BaseListener {
    void back(List<ConsignmentCompanyDto> dtos);


}

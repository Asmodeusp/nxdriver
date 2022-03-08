package com.saimawzc.freight.view.mine.setment;


import com.saimawzc.freight.dto.account.ConsignmentCompanyDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

/***
 * 托运公司
 * */
public interface ConsignCompanyView extends BaseView {

    void getCompany(List<ConsignmentCompanyDto> companyDtoList);
    void stopResh();

}

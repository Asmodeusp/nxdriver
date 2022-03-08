package com.saimawzc.freight.view.order.error;

import com.saimawzc.freight.dto.order.error.ErrorReportDto;
import com.saimawzc.freight.dto.order.error.MyErrDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface MyErrorView extends BaseView {
    void getErrorList(List<MyErrDto> myErrDtos);

}

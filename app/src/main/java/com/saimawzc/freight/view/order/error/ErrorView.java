package com.saimawzc.freight.view.order.error;

import com.saimawzc.freight.dto.order.error.ErrorReportDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface ErrorView extends BaseView {
    void getErrorType(List<ErrorReportDto>typelist);
    void successful(int type);
}

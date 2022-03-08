package com.saimawzc.freight.view.order;

import com.saimawzc.freight.dto.order.ExamGoodDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface ExamGoodView extends BaseView {

    void getExamList(List<ExamGoodDto> dto);
}

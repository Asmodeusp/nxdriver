package com.saimawzc.freight.view.mine.set;

import com.saimawzc.freight.dto.my.set.SuggestDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface MySuggestListView extends BaseView {
    void getErrorList(List<SuggestDto> myErrDtos);

}

package com.saimawzc.freight.view.sendcar;

import com.saimawzc.freight.dto.sendcar.LogistoicDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface LogistoicView extends BaseView {
    void getData(List<LogistoicDto> dto);
}

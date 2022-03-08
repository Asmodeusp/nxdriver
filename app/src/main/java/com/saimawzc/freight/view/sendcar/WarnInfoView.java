package com.saimawzc.freight.view.sendcar;

import com.saimawzc.freight.dto.sendcar.WarnInfoDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface WarnInfoView extends BaseView {
    void getData(List<WarnInfoDto>list);
}

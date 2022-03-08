package com.saimawzc.freight.view.mine.setment;



import com.saimawzc.freight.dto.account.MySetmentDto;
import com.saimawzc.freight.dto.account.MySetmentPageQueryDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface MySetmentView extends BaseView {

    void getMySetment(MySetmentPageQueryDto dtos);
    void stopResh();
}

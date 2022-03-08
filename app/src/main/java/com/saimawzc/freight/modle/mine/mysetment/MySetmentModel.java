package com.saimawzc.freight.modle.mine.mysetment;


import com.saimawzc.freight.dto.SearchValueDto;
import com.saimawzc.freight.view.mine.setment.MySetmentView;

import java.util.List;

public interface MySetmentModel {

    void getList(int page, int type, List<SearchValueDto>dtos, MySetmentView view);

    void comfirm(int type, String id, MySetmentView view);
}

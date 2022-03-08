package com.saimawzc.freight.view.order.taxi.service;


import android.app.Activity;

import com.saimawzc.freight.dto.face.FaceQueryDto;
import com.saimawzc.freight.dto.taxi.service.MyServiceListDto;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

public interface MySeriviceListView extends BaseView {


    void getList(List<MyServiceListDto>dtos);
    Activity getActivity();
    void getFaceDto(FaceQueryDto dto);

}

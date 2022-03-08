package com.saimawzc.freight.view.order;

import com.saimawzc.freight.dto.face.FaceQueryDto;
import com.saimawzc.freight.dto.order.bidd.JoinBiddDto;
import com.saimawzc.freight.view.BaseView;

/***
 * 参与竞价
 * **/
public interface JoinBiddView extends BaseView {

    void getBiddDelation(JoinBiddDto dto);

    void getFaceDto(FaceQueryDto dto);



}

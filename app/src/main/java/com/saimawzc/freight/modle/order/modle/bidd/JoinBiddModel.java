package com.saimawzc.freight.modle.order.modle.bidd;

import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.view.order.JoinBiddView;
import com.saimawzc.freight.weight.utils.listen.order.bidd.JoinBiddListener;

public interface JoinBiddModel {

    void getBiddDelation(JoinBiddView view, JoinBiddListener listener,String id);

    void addBibb(JoinBiddView view, JoinBiddListener listener,String id,String price
            ,String weight,String type,String carNum,String sijiId,String carId);

    void getsjBiddDelation(JoinBiddView view, JoinBiddListener listener,String id);

    void addsjBibb(JoinBiddView view, JoinBiddListener listener,String id,String price
            ,String weight,String type,String carNum,String carId);

    void queryFaceDto(JoinBiddView view, String carId, UserInfoDto userInfoDto);

}

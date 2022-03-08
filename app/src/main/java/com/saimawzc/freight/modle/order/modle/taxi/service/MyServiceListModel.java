package com.saimawzc.freight.modle.order.modle.taxi.service;


import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.view.order.taxi.service.MySeriviceListView;

public interface MyServiceListModel {

    void getList(MySeriviceListView view, String idcardNum,String roleId);

    void queryFaceDto(MySeriviceListView view, String carNo, String sfznum,UserInfoDto userInfoDto);
}

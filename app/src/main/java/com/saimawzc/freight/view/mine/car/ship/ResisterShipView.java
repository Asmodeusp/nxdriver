package com.saimawzc.freight.view.mine.car.ship;

import com.saimawzc.freight.dto.my.ShipTypeDo;
import com.saimawzc.freight.dto.my.car.ship.SearchShipDto;
import com.saimawzc.freight.dto.my.car.ship.ShipIsRegsister;
import com.saimawzc.freight.view.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2020/8/6.
 * 注册车辆
 */

public interface ResisterShipView extends BaseView{

    void OnDealCamera(int ype);
    String shipLength();//船舶长度
    String tranPermit();//水路运输许可证图
    String  tranNo();//运输许可证号
    String businessName();//用户名称
    String sideView();//船舶侧面图
    String drivingLicense();//行驶证
    String shipWidth();
    String shipHeight();
    String shipName();
    String shipNumberId();//船讯网ID
    String shipType();
    String shipVolume();//容积
    String invitEnter();//企业邀请码
    String ifQualification();//资质
    String allowAdd();//是否允许添加
    void getShipType(List<ShipTypeDo>dots);
    void getShipInfo(SearchShipDto dto);
    void  isResister(ShipIsRegsister dto);
    String location();




















}

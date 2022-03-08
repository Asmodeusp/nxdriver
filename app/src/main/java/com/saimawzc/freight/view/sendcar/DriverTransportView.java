package com.saimawzc.freight.view.sendcar;
import com.baidu.location.BDLocation;
import com.saimawzc.freight.dto.my.RouteDto;
import com.saimawzc.freight.dto.sendcar.TrantDto;
import com.saimawzc.freight.dto.sendcar.TrantSamllOrderDto;
import com.saimawzc.freight.view.BaseView;

public interface DriverTransportView extends BaseView {

    void getData(TrantDto delatiodto,int type);
    void location(BDLocation dblocation,String opertype);
    void getRolte(RouteDto dto,int type);

    void getSmallOrderRolte(TrantSamllOrderDto dto);
    void getCode(String code);
    void isFence(int isFenceClock,String message,String operType);

    void showCamera(int type);

}

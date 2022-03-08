package com.saimawzc.freight.modle.sendcar.model;


import android.content.Context;


import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.sendcar.ArriverOrderDto;
import com.saimawzc.freight.view.sendcar.ArriverOrderView;


import java.util.List;

public interface ArriverOrderModel {
    void getData(ArriverOrderView view, String id);
    void showCamera(Context context, BaseListener listener);
    void daka(ArriverOrderView view, String id, String type, String adress,String location,
              String pic, List<ArriverOrderDto.materialsDto>dtos,String distance,int  positioningMode,String tuneLocation );

    void daka(ArriverOrderView view, String id, String pic, List<ArriverOrderDto.materialsDto>dtos ,String type);
    void getSignWeight(ArriverOrderView view, String id);
    void isFenceClock(ArriverOrderView view,String id,String location);

    void isErrorPic(ArriverOrderView view,String id,String type);
}

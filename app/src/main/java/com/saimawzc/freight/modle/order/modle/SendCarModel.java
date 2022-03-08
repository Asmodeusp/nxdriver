package com.saimawzc.freight.modle.order.modle;
import com.saimawzc.freight.dto.order.CarInfolDto;
import com.saimawzc.freight.view.order.SendCarView;
import com.saimawzc.freight.weight.utils.listen.order.SendCarInfolListen;
import com.saimawzc.freight.weight.utils.listen.order.SendCarModellListen;

public interface SendCarModel {

    void getCarModel(SendCarView view, SendCarModellListen listener,int trantType);

    void getCarInfo(SendCarView view, SendCarInfolListen listener,
                    int page,String carTypeId,String search,int trantType,String companyId);

    void getsjCarInfo(SendCarView view, SendCarInfolListen listener,
                    int page,String carTypeId,String search,int trantType,String companyId);

    void sendCar(SendCarView view,
                 CarInfolDto.carInfoData carInfoData, String type, String billId, String sendCarNum);

    void carIsHasBeiDou(SendCarView view, String type,String id,String carId,String carNo );
}

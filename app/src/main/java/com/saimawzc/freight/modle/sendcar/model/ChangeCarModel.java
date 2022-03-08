package com.saimawzc.freight.modle.sendcar.model;


import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.view.sendcar.CompleteExecuteView;
import com.saimawzc.freight.weight.utils.listen.send.CompleteExecuteListListener;

public interface ChangeCarModel {
    void changeCar(BaseView view, String  id, SearchCarDto searchCarDto,String reason);


}

package com.saimawzc.freight.view.sendcar;

import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.sendcar.SendCarDelatiodto;
import com.saimawzc.freight.view.BaseView;

public interface SendCarDelationView extends BaseView {

    void getDelation(SendCarDelatiodto dtos,int type);
    BaseActivity getContect();
}

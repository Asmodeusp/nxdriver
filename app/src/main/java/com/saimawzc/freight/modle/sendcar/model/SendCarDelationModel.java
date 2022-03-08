package com.saimawzc.freight.modle.sendcar.model;

import com.saimawzc.freight.view.sendcar.SendCarDelationView;
import com.saimawzc.freight.weight.utils.listen.send.SendCarDelationListener;

public interface SendCarDelationModel {

    void getSendCarDelation(SendCarDelationView view, SendCarDelationListener listener,
                            String id,int type);
    void startTask(SendCarDelationView view,String id);
}

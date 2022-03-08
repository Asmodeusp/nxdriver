package com.saimawzc.freight.presenter.sendcar;

import android.content.Context;

import com.saimawzc.freight.dto.sendcar.CompleteExecuteDto;
import com.saimawzc.freight.dto.sendcar.SendCarDelatiodto;
import com.saimawzc.freight.modle.sendcar.imple.CompeleteExecuteModelImple;
import com.saimawzc.freight.modle.sendcar.imple.SendCarDelationModelImple;
import com.saimawzc.freight.modle.sendcar.model.CompleteExecuteModel;
import com.saimawzc.freight.modle.sendcar.model.SendCarDelationModel;
import com.saimawzc.freight.view.sendcar.CompleteExecuteView;
import com.saimawzc.freight.view.sendcar.SendCarDelationView;
import com.saimawzc.freight.weight.utils.listen.send.CompleteExecuteListListener;
import com.saimawzc.freight.weight.utils.listen.send.SendCarDelationListener;

import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */

public class SendCarDelationPresenter implements SendCarDelationListener {

    private Context mContext;
    SendCarDelationView view;
    SendCarDelationModel model;
    public SendCarDelationPresenter(SendCarDelationView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new SendCarDelationModelImple() ;
    }

    public void startTask(String id){
        model.startTask(view,id);

    }

    public void getData(String id,int type){
        model.getSendCarDelation(view,this,id,type);
    }

    @Override
    public void successful() {
        view.oncomplete();
    }
    @Override
    public void onFail(String str) {
        view.Toast(str);
    }

    @Override
    public void successful(int type) {


    }








    @Override
    public void getSendCarDelation(SendCarDelatiodto dtos,int type) {
        view.getDelation(dtos,type);
    }
}

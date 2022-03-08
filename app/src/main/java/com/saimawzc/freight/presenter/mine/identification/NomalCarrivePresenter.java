package com.saimawzc.freight.presenter.mine.identification;

import android.content.Context;
import android.content.Intent;

import com.baidu.ocr.ui.camera.CameraActivity;
import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.my.identification.CarrierIndenditicationDto;
import com.saimawzc.freight.modle.mine.identification.NomalCarrierModel;
import com.saimawzc.freight.modle.mine.identification.NomalCarrierModelImple;
import com.saimawzc.freight.view.mine.identificaion.NomalTaxesCarriverView;
import com.saimawzc.freight.weight.utils.FileUtil;
import com.saimawzc.freight.weight.utils.listen.identification.CarriverIdentificationListener;

/**
 * Created by Administrator on 2020/8/3.
 */

public class NomalCarrivePresenter implements BaseListener ,CarriverIdentificationListener{

    private Context mContext;
    NomalCarrierModel model;
    NomalTaxesCarriverView view;

    public NomalCarrivePresenter(NomalTaxesCarriverView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new NomalCarrierModelImple();
    }

    public void showCamera( Context context,  int type){
        model.showCamera(context, type,this) ;
    }
    public void indenfication( ){
        model.identification(view,this);
    }
    public void recarriveRz( ){
        model.reidentification(view,this);
    }

    public void dissCamera( ){
        model.dissCamera(); ;
    }
    public void getIdentificationInfo(){
        model.getIdentificationInfo(view,this);
    }
    @Override
    public void successful() {
        view.oncomplete();
    }
    @Override
    public void onFail(String str) {view.Toast(str);


    }



    @Override
    public void successful(int type) {
        view.OnDealCamera(type);
    }

    @Override
    public void driviceIndetification(CarrierIndenditicationDto identificationDto) {
        view.identificationInfo(identificationDto);
    }
}

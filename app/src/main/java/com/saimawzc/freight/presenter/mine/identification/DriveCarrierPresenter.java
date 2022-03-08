package com.saimawzc.freight.presenter.mine.identification;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.my.identification.DriviceerIdentificationDto;
import com.saimawzc.freight.modle.mine.identification.DriveLicenseModel;
import com.saimawzc.freight.modle.mine.identification.DriveLicenseModelImple;
import com.saimawzc.freight.modle.mine.identification.PersonCarrierModel;
import com.saimawzc.freight.modle.mine.identification.PersonCarrierModelImple;
import com.saimawzc.freight.view.mine.identificaion.DriveLicesenCarrierView;
import com.saimawzc.freight.view.mine.identificaion.PersonCarrierView;
import com.saimawzc.freight.weight.utils.listen.identification.DriviceIdentificationListener;

/**
 * Created by Administrator on 2020/8/3.
 */

public class DriveCarrierPresenter implements BaseListener,DriviceIdentificationListener{
    private Context mContext;
    DriveLicenseModel model;
    DriveLicesenCarrierView view;

    public DriveCarrierPresenter(DriveLicesenCarrierView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new DriveLicenseModelImple();
    }

    public void showCamera( Context context,  int type){//type 0是身份证正面  1是身份证反面
        model.showCamera(context, type,this) ;
    }

    public  void carriveRz(int isConsistent){
        model.identification(view,this,isConsistent);
    }

    public  void recarriveRz(int isConsistent){
        model.reidentification(view,this,isConsistent);
    }

    public  void getDrivicecarriInfo(){
        model.getIdentificationInfo(view,this);

    }
    public  void dissCamera(){
        model.dissCamera();
    }

    @Override
    public void successful() {//完成认证
        view.Toast("申请成功");
        view.oncomplete();
    }
    @Override
    public void onFail(String str) {
        view.Toast(str);
    }

    @Override
    public void successful(int type) {
        view.OnDealCamera(type);
    }

    @Override
    public void driviceIndetification(DriviceerIdentificationDto identificationDto) {
        view.getInditifacationInfo(identificationDto);
    }
}

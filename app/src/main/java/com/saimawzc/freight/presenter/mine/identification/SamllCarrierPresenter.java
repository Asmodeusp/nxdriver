package com.saimawzc.freight.presenter.mine.identification;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.my.identification.CarrierIndenditicationDto;
import com.saimawzc.freight.modle.mine.identification.PersonCarrierModel;
import com.saimawzc.freight.modle.mine.identification.PersonCarrierModelImple;
import com.saimawzc.freight.modle.mine.identification.SmallCarrierModel;
import com.saimawzc.freight.modle.mine.identification.SmallCarrierModelImple;
import com.saimawzc.freight.view.mine.identificaion.PersonCarrierView;
import com.saimawzc.freight.view.mine.identificaion.SmallCompanyCarrierView;
import com.saimawzc.freight.weight.utils.listen.identification.CarriverIdentificationListener;

/**
 * Created by Administrator on 2020/8/3.
 */

public class SamllCarrierPresenter implements BaseListener,CarriverIdentificationListener{
    private Context mContext;
    SmallCarrierModel model;
    SmallCompanyCarrierView view;

    public SamllCarrierPresenter(SmallCompanyCarrierView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new SmallCarrierModelImple();
    }

    public void showCamera( Context context,  int type){//type 0是身份证正面  1是身份证反面

        model.showCamera(context, type,this) ;
    }
    public void dissCamera( ){

        model.dissCamera(); ;
    }
    public  void carriveRz(){
        model.identification(view,this);
    }
    public  void recarriveRz(){
        model.reidentification(view,this);
    }

    public void getIdentificationInfo(){
        model.getIdentificationInfo(view,this);
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
    public void driviceIndetification(CarrierIndenditicationDto identificationDto) {
        view.identificationInfo(identificationDto);
    }
}

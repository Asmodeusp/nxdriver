package com.saimawzc.freight.presenter.sendcar;

import android.content.Context;

import com.saimawzc.freight.dto.LcInfoDto;
import com.saimawzc.freight.dto.sendcar.WaitExecuteDto;
import com.saimawzc.freight.modle.sendcar.imple.WaitExecuteModelImple;
import com.saimawzc.freight.modle.sendcar.model.WaitExecuteModel;
import com.saimawzc.freight.view.sendcar.WaitExecuteView;
import com.saimawzc.freight.weight.utils.listen.send.WaitExecuteListListener;

import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */

public class WaitExcecutePresenter implements WaitExecuteListListener {

    private Context mContext;
    WaitExecuteView view;
    WaitExecuteModel model;
    public WaitExcecutePresenter(WaitExecuteView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new WaitExecuteModelImple() ;
    }

    public void getData(int page, String type,String value){
        model.getSendLsit(view,this,page,type,value);
    }
    public void getLcInfoDto(String lcbh, String dispatchCarId, String czbm,String companyId){
        model.getLcInfoDto(view,this,lcbh,dispatchCarId,czbm,companyId);
    }
    public void siloScanLock(String dispatchCarId, String dispatchCarNo, String lcbh){
        model.siloScanLock(view,this,dispatchCarId,dispatchCarNo,lcbh);
    }
    public void siloScanUnlock(String dispatchCarId, String dispatchCarNo, String lcbh){
        model.siloScanUnlock(view,this,dispatchCarId,dispatchCarNo,lcbh);
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
    public void startTask(String id,int position){
        model.startTask(view,id,position);

    }




    @Override
    public void getDatas(List<WaitExecuteDto.WaitExecuteData> dtos) {
        view.getSendCarList(dtos);
    }

    @Override
    public void getLcInfoDto(LcInfoDto dtos) {
        view.getLcInfoDto(dtos);
    }
}

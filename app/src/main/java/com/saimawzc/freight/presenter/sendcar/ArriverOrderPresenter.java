package com.saimawzc.freight.presenter.sendcar;

import android.content.Context;
import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.sendcar.ArriverOrderDto;
import com.saimawzc.freight.modle.sendcar.imple.ArriverOrderModelImple;
import com.saimawzc.freight.modle.sendcar.model.ArriverOrderModel;
import com.saimawzc.freight.view.sendcar.ArriverOrderView;
import java.util.List;
/**
 * Created by Administrator on 2020/7/30.
 */

public class ArriverOrderPresenter implements BaseListener {

    private Context mContext;
    ArriverOrderView view;
    ArriverOrderModel model;
    public ArriverOrderPresenter(ArriverOrderView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ArriverOrderModelImple() ;
    }

    public void getData(String id){
        model.getData(view,id);
    }
    public void showCamera( Context context){
        model.showCamera(context,this) ;
    }

    public void daka(String id , String type,String adress,String location, String pic,
                     List<ArriverOrderDto.materialsDto>dtos,String distance,int  positioningMode,String tuneLocation){
        model.daka(view,id,type,adress,location,pic,dtos,distance,positioningMode,tuneLocation);
    }

    public void daka(String id ,  String pic,
                     List<ArriverOrderDto.materialsDto>dtos,String type){
        model.daka(view,id,pic,dtos,type);
    }

    public void getSignWeight(String id){
        model.getSignWeight(view,id);

    }

    public void isFeeced(String id,String location){
        model.isFenceClock(view,id,location );
    }

    public void  isErrorPic(String id,String type){
        model.isErrorPic(view,id,type);
    }

    @Override
    public void successful() {

    }

    @Override
    public void onFail(String str) {

    }

    @Override
    public void successful(int type) {
        view.ondealCamera(type);

    }
}

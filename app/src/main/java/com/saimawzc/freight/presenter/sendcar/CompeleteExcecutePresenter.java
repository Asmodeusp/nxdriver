package com.saimawzc.freight.presenter.sendcar;

import android.content.Context;

import com.saimawzc.freight.dto.sendcar.CompleteExecuteDto;
import com.saimawzc.freight.modle.sendcar.imple.CompeleteExecuteModelImple;
import com.saimawzc.freight.modle.sendcar.model.CompleteExecuteModel;
import com.saimawzc.freight.view.sendcar.CompleteExecuteView;
import com.saimawzc.freight.weight.utils.listen.send.CompleteExecuteListListener;

import java.util.List;
/**
 * Created by Administrator on 2020/7/30.
 */

public class CompeleteExcecutePresenter implements CompleteExecuteListListener {

    private Context mContext;
    CompleteExecuteView view;
    CompleteExecuteModel model;
    public CompeleteExcecutePresenter(CompleteExecuteView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new CompeleteExecuteModelImple() ;
    }

    public void getData(int page, String type,String value,String starttime,String endtime,int status){
        model.getSendLsit(view,this,page,type,value,starttime,endtime,status);
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
    public void getDatas(List<CompleteExecuteDto.ComeletaExecuteData> dtos) {
        view.getSendCarList(dtos);

    }
}

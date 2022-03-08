package com.saimawzc.freight.presenter.order.bill;
import android.content.Context;

import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.dto.order.bidd.JoinBiddDto;
import com.saimawzc.freight.modle.order.modelImple.bidd.JoinBiddModelImple;
import com.saimawzc.freight.modle.order.modle.bidd.JoinBiddModel;
import com.saimawzc.freight.view.order.JoinBiddView;
import com.saimawzc.freight.weight.utils.listen.order.bidd.JoinBiddListener;

import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */

public class JoinBiddPresenter implements JoinBiddListener {

    private Context mContext;
    JoinBiddView view;
    JoinBiddModel model;

    public JoinBiddPresenter(JoinBiddView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new JoinBiddModelImple() ;
    }

    public void getBiddDelation(String id){
        model.getBiddDelation(view,this,id);
    }
    public void  addBidd(String id,String price,String weight,String type,String carNum,String sijiId,String carId){
        model.addBibb(view,this,id,price,weight,type,carNum,sijiId,carId);
    }

    public void getsjBiddDelation(String id){
        model.getsjBiddDelation(view,this,id);
    }

    public void  addsjBidd(String id,String price,String weight,String type,String carNum,String carId){
        model.addsjBibb(view,this,id,price,weight,type,carNum,carId);
    }


    public void getFaceQueryDto(String carId, UserInfoDto userInfoDto){
        model.queryFaceDto(view,carId,userInfoDto);

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
    public void getBiddDelation(JoinBiddDto dtos) {
        view.getBiddDelation(dtos);

    }
}

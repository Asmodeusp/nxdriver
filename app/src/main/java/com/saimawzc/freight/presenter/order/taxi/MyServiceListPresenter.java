package com.saimawzc.freight.presenter.order.taxi;

import android.content.Context;

import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.dto.taxi.TjSubmitDto;
import com.saimawzc.freight.modle.order.modelImple.taxi.PublisherModelImple;
import com.saimawzc.freight.modle.order.modelImple.taxi.servive.MyServiceListModelImple;
import com.saimawzc.freight.modle.order.modle.taxi.PublisherModel;
import com.saimawzc.freight.modle.order.modle.taxi.service.MyServiceListModel;
import com.saimawzc.freight.view.order.taxi.PublisherView;
import com.saimawzc.freight.view.order.taxi.service.MySeriviceListView;

/**
 *
 */

public class MyServiceListPresenter {

    private Context mContext;
    MySeriviceListView view;
    MyServiceListModel model;

    public MyServiceListPresenter(MySeriviceListView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MyServiceListModelImple() ;
    }
    public void getData(String idcard,String roleId){
        model.getList(view,idcard,roleId);
    }
    public void getFaceData(String carNo, String idCard, UserInfoDto userInfoDto){
        model.queryFaceDto(view,carNo,idCard,userInfoDto);
    }
}

package com.saimawzc.freight.presenter.mine.person;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.modle.mine.person.PersonCenterModel;
import com.saimawzc.freight.modle.mine.person.PersonCenterModelImple;
import com.saimawzc.freight.view.mine.person.PersonCenterView;

/**
 * Created by Administrator on 2020/8/8.
 */

public class PersonCenterPresenter implements BaseListener {

    private Context mContext;
    PersonCenterModel model;
    PersonCenterView view;

    public PersonCenterPresenter(PersonCenterView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new PersonCenterModelImple();
    }

    public void showCamera(){
        model.showCamera(mContext,this);
    }
    public void changePic(){
        model.changePic(view,this);
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
        view.OnDealCamera(type);
    }
}

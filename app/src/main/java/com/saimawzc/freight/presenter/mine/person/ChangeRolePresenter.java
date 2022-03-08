package com.saimawzc.freight.presenter.mine.person;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.modle.mine.person.ChangeRoleModel;
import com.saimawzc.freight.modle.mine.person.ChangeRoleModelImple;
import com.saimawzc.freight.view.mine.person.ChangeRoleView;

/**
 * Created by Administrator on 2020/7/30.
 */

public class ChangeRolePresenter implements BaseListener {

    private Context mContext;
    ChangeRoleView view;
    ChangeRoleModel model;
    public ChangeRolePresenter(ChangeRoleView iLoginView, Context context) {
        this.view = iLoginView;
        this.mContext = context;
        model=new ChangeRoleModelImple() ;
    }

    public void changeRole(int role){//角色 1货主 2承运商 3司机 4收货人
        model.changeRole(view,this,role);
    }

    @Override
    public void successful() {

    }
    @Override
    public void onFail(String str) {
        view.Toast(str);
    }

    @Override
    public void successful(int type) {
        view.oncomplete(type);

    }
}

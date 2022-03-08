package com.saimawzc.freight.presenter.mine;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.modle.mine.MineModel;
import com.saimawzc.freight.modle.mine.MineModelImple;
import com.saimawzc.freight.modle.mine.person.PersonCenterModel;
import com.saimawzc.freight.modle.mine.person.PersonCenterModelImple;
import com.saimawzc.freight.view.mine.MineView;
import com.saimawzc.freight.view.mine.person.PersonCenterView;

/**
 * Created by Administrator on 2020/8/8.
 */

public class MinePersonter  {

    private Context mContext;
    MineModel model;
    MineView view;

    public MinePersonter(MineView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MineModelImple();
    }

    public void getPerson(){
        model.getPerson(view);
    }
    public void getCarriveList(){
        model.getCarrierList(view);
    }
    public void getLessess(){
        model.getLessessList(view);
    }

   /**获取子账户信息**/
    public void getSonAccount(){
        model.getSonAccount(view);

    }

}

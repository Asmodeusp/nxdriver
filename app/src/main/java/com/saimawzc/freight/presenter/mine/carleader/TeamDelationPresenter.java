package com.saimawzc.freight.presenter.mine.carleader;

import android.content.Context;

import com.saimawzc.freight.modle.mine.carleader.TeamDelationModel;
import com.saimawzc.freight.modle.mine.carleader.imple.TeamDelationModelImple;
import com.saimawzc.freight.view.mine.carleader.TeamDelationView;
/**
 * Created by Administrator on 2020/8/10.
 */

public class TeamDelationPresenter {
    private Context mContext;
    TeamDelationModel model;
    TeamDelationView view;


    public TeamDelationPresenter(TeamDelationView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new TeamDelationModelImple();
    }

    public void getDelation(String  id){
        model.getData(view,id);
    }

    public void getPerInfo(String  id){
        model.getPerInfo(view,id);
    }

}

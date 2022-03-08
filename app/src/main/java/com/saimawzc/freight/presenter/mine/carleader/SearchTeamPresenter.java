package com.saimawzc.freight.presenter.mine.carleader;

import android.content.Context;
import com.saimawzc.freight.modle.mine.carleader.SearchTeamModel;
import com.saimawzc.freight.modle.mine.carleader.imple.SearchTeamModelImple;
import com.saimawzc.freight.view.mine.carleader.SearchTeamView;

/**
 * Created by Administrator on 2020/8/10.
 */

public class SearchTeamPresenter {
    private Context mContext;
    SearchTeamModel model;
    SearchTeamView view;


    public SearchTeamPresenter(SearchTeamView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new SearchTeamModelImple();
    }

    public void getData(String carNo,String phone){
        model.getData(view,carNo,phone);

    }



}

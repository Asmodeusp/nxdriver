package com.saimawzc.freight.presenter.mine.carleader;

import android.content.Context;

import com.saimawzc.freight.dto.wallet.BindBankDto;
import com.saimawzc.freight.modle.mine.carleader.CreatTeamModel;
import com.saimawzc.freight.modle.mine.carleader.imple.CreatTeamModelImple;
import com.saimawzc.freight.view.mine.carleader.CreatTeamView;

public class CreatTeamPresenter {
    private Context mContext;
    CreatTeamView view;
    CreatTeamModel model;

    public CreatTeamPresenter(CreatTeamView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new CreatTeamModelImple() ;
    }
    public void bind(BindBankDto dto){
        model.bind(view,dto);
    }

    public void cardBin(String cardNo){
        model.cardBin(view,cardNo);
    }

}

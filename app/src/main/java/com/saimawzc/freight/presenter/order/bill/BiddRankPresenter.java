package com.saimawzc.freight.presenter.order.bill;

import android.content.Context;

import com.saimawzc.freight.modle.order.modelImple.bidd.BiddRankModelImple;
import com.saimawzc.freight.modle.order.modle.bidd.RankBiddModel;
import com.saimawzc.freight.view.order.BiddRandView;


/**
 * Created by Administrator on 2020/8/3.
 */

public class BiddRankPresenter {
    private Context mContext;
    BiddRandView view;
    RankBiddModel model;

    public BiddRankPresenter(BiddRandView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new BiddRankModelImple();
    }

    public void getRankList(int page,String id){
        model.getRankLsit(view,page,id);
    }
}

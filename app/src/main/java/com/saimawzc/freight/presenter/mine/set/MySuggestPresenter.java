package com.saimawzc.freight.presenter.mine.set;

import android.content.Context;
import com.saimawzc.freight.modle.mine.set.MySuggestModel;
import com.saimawzc.freight.modle.mine.set.MySuggestModelImple;
import com.saimawzc.freight.view.mine.set.MySuggestListView;
/**
 * Created by Administrator on 2020/7/30.
 */

public class MySuggestPresenter {

    private Context mContext;
    MySuggestListView view;
    MySuggestModel model;
    public MySuggestPresenter(MySuggestListView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MySuggestModelImple() ;
    }

    public void getErrorType(){
        model.getSuggestList(view);
    }

}

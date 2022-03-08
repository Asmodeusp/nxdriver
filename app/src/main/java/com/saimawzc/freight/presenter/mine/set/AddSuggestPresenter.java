package com.saimawzc.freight.presenter.mine.set;

import android.content.Context;
import com.saimawzc.freight.modle.mine.set.AddSuggestModel;
import com.saimawzc.freight.modle.mine.set.AddSuggestModelImple;
import com.saimawzc.freight.view.mine.set.AddSuggetsView;
/**
 * Created by Administrator on 2020/7/30.
 */

public class AddSuggestPresenter  {

    private Context mContext;
    AddSuggetsView view;
    AddSuggestModel model;
    public AddSuggestPresenter(AddSuggetsView iLoginView, Context context) {
        this.view = iLoginView;
        this.mContext = context;
        model=new AddSuggestModelImple() ;
    }
    public void submit(String contect,String pic){
        model.submit(view,contect,pic);
    }

    public void showCamera(Context context){
        model.showCamera(view,context) ;
    }
}

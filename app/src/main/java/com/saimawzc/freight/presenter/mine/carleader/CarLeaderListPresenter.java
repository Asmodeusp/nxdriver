package com.saimawzc.freight.presenter.mine.carleader;
import android.content.Context;
import com.saimawzc.freight.modle.mine.carleader.imple.CarLeaderListModelImple;
import com.saimawzc.freight.modle.mine.carleader.CarLeaderModel;
import com.saimawzc.freight.view.mine.carleader.CarLeaderListView;
/**
 * Created by Administrator on 2020/8/10.
 */

public class CarLeaderListPresenter {
    private Context mContext;
    CarLeaderModel model;
    CarLeaderListView view;


    public CarLeaderListPresenter(CarLeaderListView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new CarLeaderListModelImple();
    }

    public void getCarLeaderList(int page){
        model.getData(view,page);

    }



}

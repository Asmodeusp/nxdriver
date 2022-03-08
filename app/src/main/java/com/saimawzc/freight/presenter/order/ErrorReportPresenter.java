package com.saimawzc.freight.presenter.order;

import android.content.Context;
import com.saimawzc.freight.modle.order.modelImple.ErrorModelImple;
import com.saimawzc.freight.modle.order.modle.bidd.ErrorModel;
import com.saimawzc.freight.view.order.error.ErrorView;
import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */

public class ErrorReportPresenter   {

    private Context mContext;
    ErrorView view;
    ErrorModel model;
    public ErrorReportPresenter(ErrorView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ErrorModelImple() ;
    }

    public void getErrorType(){
        model.getErrorType(view);
    }
    public void AddError(String id,String exceptionTypeId,String exceptionName,String exceptionImage,String location){
        model.submitError(view,id,exceptionTypeId,exceptionName,exceptionImage,location);
    }
    public void showCamera(Context context){
        model.showCamera(view,context) ;
    }
}

package com.saimawzc.freight.modle.order.modle.bidd;



import android.content.Context;

import com.saimawzc.freight.view.order.error.ErrorView;

import java.util.List;

public interface ErrorModel {
    void getErrorType(ErrorView view);
    void submitError(ErrorView view,String id,String exceptionTypeId,String exceptionName,String exceptionImage,String location );
    void showCamera(ErrorView view, Context context);
}

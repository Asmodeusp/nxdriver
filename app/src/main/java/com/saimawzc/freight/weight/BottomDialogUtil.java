package com.saimawzc.freight.weight;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.saimawzc.freight.R;
import com.saimawzc.freight.weight.utils.dialog.BottomDialog;

import java.util.List;


public class BottomDialogUtil {

    private BottomDialog  bottomDialog;


    private Context mContext;
    public BottomDialogUtil(Builder builder){
        mContext = builder.context;
        bottomDialog =
                new BottomDialog(mContext, R.style.BaseDialog,builder.contentviewid);
        bottomDialog.setCancelable(builder.outsidecancel);
        bottomDialog.setCanceledOnTouchOutside(builder.outsidecancel);

    }
    /**
     * popup 消失
     */
    public void dismiss(){
        if (bottomDialog != null&&isContextExisted(mContext)){
            bottomDialog.dismiss();
        }
    }

    public BottomDialogUtil show(){
        if (bottomDialog != null&&isContextExisted(mContext)){
            bottomDialog.show();
        }
        return this;
    }

    /**
     * 根据id获取view
     * @param viewid
     * @return
     */
    public View getItemView(int viewid){
        if (bottomDialog != null){
            return this.bottomDialog.findViewById(viewid);
        }
        return null;
    }

    public void setOnClickListener(int viewid,View.OnClickListener listener){
        View view = getItemView(viewid);
        view.setOnClickListener(listener);
    }
    /**
     * builder 类
     */
    public static class Builder{
        private int contentviewid;
        private boolean outsidecancel;
        private Context context;
        public Builder setContext(Context context){
            this.context = context;
            return this;
        }

        public Builder setContentView(int contentviewid){
            this.contentviewid = contentviewid;
            return this;
        }


        public Builder setOutSideCancel(boolean outsidecancel){
            this.outsidecancel = outsidecancel;
            return this;
        }

        public BottomDialogUtil builder(){
            return new BottomDialogUtil(this);
        }
    }


    public  boolean isContextExisted(Context context) {
        if (context != null) {
            if (context instanceof Activity) {
                if (!((Activity)context).isFinishing()) {
                    return true;
                }
            } else if (context instanceof Service) {
                if (isServiceExisted(context, context.getClass().getName())) {
                    return true;
                }
            } else if (context instanceof Application) {
                return true;
            }
        }
        return false;
    }
    public  boolean isServiceExisted(Context context, String className) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(i);
            ComponentName serviceName = serviceInfo.service;

            if (serviceName.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

}

package com.saimawzc.freight.weight.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static Toast mToast;
 
    public static void ShowMsg(Context context, String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, duration);
        }else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
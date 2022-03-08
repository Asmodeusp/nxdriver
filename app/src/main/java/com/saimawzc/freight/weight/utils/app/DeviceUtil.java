package com.saimawzc.freight.weight.utils.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

/**
 * Author : zhouyx
 * Date   : 2015/9/6
 */
public class DeviceUtil {



    /**
     * 获取手机名称
     * @return
     */
    public static String getDeviceName(){
        return android.os.Build.MANUFACTURER;
    }
    /**
     * 获取应用名称
     *
     * @param context
     * @return
     */
    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        return packageManager.getApplicationLabel(applicationInfo).toString();
    }
}

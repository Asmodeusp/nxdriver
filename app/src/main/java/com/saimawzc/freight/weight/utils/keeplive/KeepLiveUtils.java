package com.saimawzc.freight.weight.utils.keeplive;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
/******
 * 保活  1 引导白名单
 *       2 1像素保活
 *       3 前台服务保活
 *       4 账号同步拉活 时间不确定
 *       5 JobSeclue 拉活
 * *********/
public class KeepLiveUtils {

    /****判断是否为华为手机***/
    public boolean isHuawei() {
        if(Build.BRAND == null) {
            return false;
        } else{
            return Build.BRAND.toLowerCase().equals("huawei") || Build.BRAND.toLowerCase().equals("honor");
        }
    }
    /***跳转华为手机管理页面***/
    public void goHuaweiSetting(Context context) {
        try{
            showActivity("com.huawei.systemmanager",
                    "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity",context);
        } catch(Exception e) {
//            showActivity("com.huawei.systemmanager",
//                    "com.huawei.systemmanager.optimize.bootstart.BootStartActivity",context);
        }
    }


    /****判断是否小米手机**/
    public static boolean isXiaomi() {
        return Build.BRAND != null&& Build.BRAND.toLowerCase().equals("xiaomi");
    }

    /***跳转小米安全中心的自启动管理页面****/
    public void goXiaomiSetting(Context context) {
        showActivity("com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity",context);
    }

    /***判断是否OPPO***/
    public static boolean isOPPO() {
        return Build.BRAND != null&& Build.BRAND.toLowerCase().equals("oppo");
    }
    /****跳转OPPO手机管理页面***/
    public void goOPPOSetting(Context context) {
        try{
            showActivity("com.coloros.phonemanager",context);
        } catch(Exception e1) {
            try{
                showActivity("com.oppo.safe",context);
            } catch(Exception e2) {
                try{
                    showActivity("com.coloros.oppoguardelf",context);
                } catch(Exception e3) {
                    showActivity("com.coloros.safecenter",context);
                }
            }
        }
    }

    /***判断是否为VIVO手机****/
    public static boolean isVIVO() {
        return Build.BRAND != null&& Build.BRAND.toLowerCase().equals("vivo");
    }
    /***跳转VIVO手机管理****/
    public void goVIVOSetting(Context context) {
        showActivity("com.iqoo.secure",context);
    }

    /***判断是否魅族****/
    public static boolean isMeizu() {
        return Build.BRAND != null&& Build.BRAND.toLowerCase().equals("meizu");
    }
    /****跳转魅族管理***/
    public void goMeizuSetting(Context context) {
        showActivity("com.meizu.safe",context);
    }

    /****判断是否为3星***/
    public static boolean isSamsung() {
        return Build.BRAND != null&& Build.BRAND.toLowerCase().equals("samsung");
    }
    /****跳转三星智能管理器***/
    public void goSamsungSetting(Context context) {
        try{
            showActivity("com.samsung.android.sm_cn",context);
        } catch(Exception e) {
            showActivity("com.samsung.android.sm",context);
        }
    }
    /****判断是否为乐视***/
    public static boolean isLeTV() {
        return Build.BRAND != null&& Build.BRAND.toLowerCase().equals("letv");
    }

    /****跳转乐视管理器****/
    public void goLetvSetting(Context context) {
        showActivity("com.letv.android.letvsafe",
                "com.letv.android.letvsafe.AutobootManageActivity",context);
    }

    /****判断是否为锤子***/
    public static boolean isSmartisan() {
        return Build.BRAND != null&& Build.BRAND.toLowerCase().equals("smartisan");
    }

    /****跳转锤子管理器****/
    public void goSmartisanSetting(Context context) {
        showActivity("com.smartisanos.security",context);
    }


    /****判断是否为酷派***/
    public static boolean isCOOLPAD() {
        return Build.BRAND != null&& Build.BRAND.toLowerCase().equals("coolpad");
    }


    /****判断是否联想***/
    public static boolean isLENOVO() {
        return Build.BRAND != null&& Build.BRAND.toLowerCase().equals("lenovo");//LENOVO
    }


    /****判断是否中兴***/
    public static boolean isZTE() {
        return Build.BRAND != null&& Build.BRAND.toLowerCase().equals("zte");
    }



    /**
     * 跳转到指定应用的首页
     */
    private void showActivity(@NonNull String packageName, Context c) {
        Intent intent = c.getPackageManager().getLaunchIntentForPackage(packageName);
        c.startActivity(intent);
    }

    /**
     * 跳转到指定应用的指定页面
     */
    private void showActivity(@NonNull String packageName, @NonNull String activityDir,Context c) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityDir));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        c. startActivity(intent);
    }
}

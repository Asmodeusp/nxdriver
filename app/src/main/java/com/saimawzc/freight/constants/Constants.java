package com.saimawzc.freight.constants;

import android.os.Environment;

import com.saimawzc.freight.base.BaseActivity;

/**
 * 作者：
 * 邮箱：
 */
public class Constants {

    /***测试***/
// public static final String Baseurl ="http://180.76.240.138:8005/";//
// public static final String Baseurl ="http://120.48.17.182:8005/";//  http://106.12.165.54:8005//
// public static final String Baseurl ="http://106.12.165.54:8005/";//  http://106.12.165.54:8005//
// public static final String Baseurl ="http://192.168.102.8:8005/";//  http://106.12.165.54:8005//

// public static final String Baseurl ="http://192.168.101.29:8005/";//  http://106.12.165.54:8005/
// public static final String baseSwUrl = "http://106.12.165.54:8201/";

    /***正式***/
    public static final String Baseurl = "https://app.api.wzcwlw.com/";
    public static final String baseSwUrl = "http://taxation.api.wzcwlw.com/";
    public static int DEVICE_FIRM = -1;
    public static final String APK_SAVE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/nxdriver/" + BaseActivity.getCurrentTime("yyyy-MM-dd HH:mm") + "siji.apk";

    /*****
     * 广播
     * **/
    public static final String reshSj = "reshsj";//更新司机
    public static final String reshYunDn = "reshYunDn";//更新运单
    public static final String reshYunDnsJ = "reshYunDnSJ";//更新运单
    public static final String reshCar = "reshCar";//更新司机
    public static final String reshLessess = "reshLessess";//更新司机
    public static final String reshCarrive = "reshCarrive";//
    public static final String reshTrant = "reshTrant";//更新运输
    public static final String reshCall = "reshCall";//拨打电话之后刷新
    public static final String reshShip = "reshShip";//更新司机
    public static final String reshMyDriver = "reshMyDriver";
    public static final String reshAuroAdto = "reshAuroAdto";//更新自动签收
    public static final String reshAccount_confirm = "reshAccount";//更新结算单
    public static final String reshAccount_unconfirm = "reshAccountun_confirm";//更新结算单
    public static final String reshService = "reshService";//刷新服务方
    public static final String reshCarLeaderList = "reshCarLeaderList";//刷新车队长列表
    public static final String reshTeamDelation = "reshTeamDelation";//刷新车队长详情
    public static final String reshMyQueue = "reshMyQueue";//刷新我的车队长
    public static final String reshMainOrder = "reshMainOrder";//更新首页待确认
    public static final String openTruck = "openTruck";//开启鹰眼
    public static final String reshReQS = "reshReQS";//重新签收
    public static final String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";

    public static final String reshChangeCYS = "reshChangeCYS";//承运商更新

    public static final String reshChange = "reshChange";//承运商更新
    // quality类型：0：正常、1：宽松、2：严格、3：自定义
    public static final int QUALITY_NORMAL = 0;
    public static final int QUALITY_LOW = 1;
    public static final int QUALITY_HIGH = 2;
    public static final int QUALITY_CUSTOM = 3;
    
}

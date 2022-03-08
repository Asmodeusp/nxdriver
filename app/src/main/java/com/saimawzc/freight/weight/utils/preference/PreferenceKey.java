package com.saimawzc.freight.weight.utils.preference;

/**
 * @Description: 用于存放Preference的Key
 */
public class PreferenceKey {



    public static final String ID = "id";
    /**
     * android系统版本是否低于4.4
     * 是则返回true
     */
    public static final String VERSION = "version";

    public static final String USER_INFO = "user_info";

    public static final String CITY_INFO = "city_info";

    public static final String PERSON_CENTER = "personcenter";

    public static final String CURRENT_CAR_NO = "current_carno";


    public static final String LOGIN_TYPE = "logintype";//登录角色
    public static final String CITY_Link_Options2Items = "options12tems";
    public static final String CITY_Link_Options3Items = "options13tems";

    public static final String USER_ACCOUNT = "account";
    public static final String PASS_WORD = "pass_word";
    public static final String ISREMEMBER_PASS = "is_remember_pass";//1已经记住

    public static final String IS_INTOFENCE = "is_into_fence";//是否进入围栏

    public static final String changeAPPtime = "change_app_time";//修改APP时间

    public static final String TIpMain = "TIpMain";//首页新手引导

    public static final String TIpmINE = "TIpmINE";//我的页面新手引导
    public static final String TIpmSet = "TIpmSet";//设置页面新手引导
    public static final String ISREAD_AQXZ = "ISREAD_AQXZ";//是否阅读安全须知

    public static final String READ_PRIVACY = "privacy";//是否已经阅读隐私政策
    public static final String DRIVER_IS_INDENFICATION = "driver_is_rz";//司机是否认证  1 已经认证 其他未认证

    public static final String CYS_IS_INDENFICATION = "cys_is_rz";//承运商是否认证 1 已经认证 其他未认证
    public static final String isChange_or_login = "isChange_or_login";//是否重新登录
    public static final String ISCAR_ACHE = "is_carsearch_ache";//需要缓存

    public static final String AUTO_SIGN_DTO = "autosing";//自动签收
    public static final String AUTO_SIGN_WL_NUM = "autosing_wl_num";//围栏数量
    public static final String AlreadyUploadWl = "AlreadyUploadWl";//已经上报的高危围栏

    public static final String OLD_UPDATE_TIME = "oldupdatime";//上次更新时间


    public static final String Fingerprint_Account = "Fingerprint_Account";//
    public static final String Fingerprint_Pass = "Fingerprint_Pass";//
    public static final String Fingerprint_Type = "Fingerprint_Type";//



}

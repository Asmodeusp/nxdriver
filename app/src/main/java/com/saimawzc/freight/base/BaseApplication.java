package com.saimawzc.freight.base;
import android.app.AlarmManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.hdgq.locationlib.keeplive.KeepLive;
import com.hdgq.locationlib.keeplive.config.ForegroundNotification;
import com.hdgq.locationlib.keeplive.config.ForegroundNotificationClickListener;
import com.hdgq.locationlib.keeplive.config.KeepLiveService;
import com.mob.MobSDK;
import com.saimawzc.freight.BuildConfig;
import com.saimawzc.freight.constants.Constants;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.navisdk.adapter.BaiduNaviManagerFactory;
import com.baidu.navisdk.adapter.IBaiduNaviManager;
import com.baidu.navisdk.adapter.struct.BNTTsInitConfig;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.entity.LocRequest;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.track.LatestPointRequest;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.model.BaseRequest;
import com.baidu.trace.model.ProcessOption;
import com.bumptech.glide.Glide;
import com.saimawzc.freight.R;
import com.saimawzc.freight.dto.login.AreaDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.ui.DriverMainActivity;
import com.saimawzc.freight.ui.login.LoginActivity;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.hawk.HawkBuilder;
import com.saimawzc.freight.weight.utils.hawk.LogLevel;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.loadimg.GlideImageLoader;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;
import com.saimawzc.freight.constants.AppConfig;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.saimawzc.freight.weight.utils.serves.RepetitionService;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.loopshare.LoopShareResultListener;
import static com.baidu.mapapi.NetworkUtil.isNetworkAvailable;
/**
 * Author:nsj
 * Date: 2020/7/27
 */
public class BaseApplication extends Application {

    public static BaseApplication instance;
    public static ArrayList<AreaDto> options1Items;

    public static final String TAG = "NXDRIVER";
    private UserInfoDto userInfoDto;
    private Notification notification = null;

    /****
     *????????????
     * ***/
    private AtomicInteger mSequenceGenerator = new AtomicInteger();
    /**
     * ???????????????
     */
    public LBSTraceClient mClient = null;
    /**
     * ????????????
     */
    public Trace mTrace = null;
    public SharedPreferences trackConf = null;
    public LocRequest locRequest = null;
    /**
     * ????????????ID
     */
    public long serviceId = 223521;
    /**
     * Entity??????
     */
    public String entityName = "????????????";

    public boolean isRegisterReceiver = false;
    /**
     * ????????????????????????
     */
    public boolean isTraceStarted = false;
    /**
     * ????????????????????????
     */
    public boolean isGatherStarted = false;
    public static int screenWidth = 0;
    public static int screenHeight = 0;

    // ????????????????????????
    public static List<LivenessTypeEnum> livenessList = new ArrayList<>();
    // ??????????????????
    public static boolean isLivenessRandom = true;
    // ??????????????????
    public static boolean isOpenSound = true;
    // ??????????????????
    public static boolean isActionLive = true;
    // ???????????????0????????????1????????????2????????????3???????????????
    public static int qualityLevel = Constants.QUALITY_NORMAL;
    /****
     *??????
     * ***/
    @Override
    public void onCreate() {
        super.onCreate();
//        /**??????????????????????????????????????????????????????????????????TreadPolicy???????????????VM????????????VmPolicy???*/
        if (AppConfig.Relase && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().detectNetwork().
                    penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().
                    detectFileUriExposure().penaltyLog().build());
        }
        //?????????Hawk
        Hawk.init(getApplicationContext())
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSqliteStorage(getApplicationContext()))
                .setLogLevel(LogLevel.FULL)
                .build();
        //?????????????????????
        Http.initHttp(this);
        userInfoDto=Hawk.get(PreferenceKey.USER_INFO);
        if(userInfoDto!=null){
            entityName=userInfoDto.getRoleId();
        }
        ImageLoadUtil.getInstance();
        instance = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                options1Items=Hawk.get(PreferenceKey.CITY_INFO);
            }
        }).start();
        /***  ?????????galallfinal ***/
        initGalalleryFinal();
        if (BuildConfig.DEBUG) {
            initDebugTool();
        } else {// ???release??????????????????????????????????????????
            CrashReport.UserStrategy strategy=new CrashReport.UserStrategy(getApplicationContext());
            strategy.setAppChannel("myChannel");
            strategy.setAppVersion(BaseActivity.getVersionName(getApplicationContext()));
            strategy.setAppPackageName("com.saimawzc.freight");
            CrashReport.initCrashReport(getApplicationContext(), "7182377ce0", true,strategy);
        }

        try{
            SDKInitializer.initialize(this);
            initNavi();
            initNotification();
        }catch (Exception e){
        }
        try {
            if(!TextUtils.isEmpty(entityName)){
                if(!entityName.equals("????????????")){
                    Log.e("msg","????????????="+entityName);
                    mClient = new LBSTraceClient(this);
                    mTrace = new Trace(serviceId, entityName);
                    trackConf = getSharedPreferences("track_conf", MODE_PRIVATE);
                    locRequest = new LocRequest(serviceId);
                    // ????????????(??????:???)
                    int gatherInterval = 10;
                    // ??????????????????(??????:???)
                    int packInterval = 60;
                    // ???????????????????????????
                    mClient.setInterval(gatherInterval, packInterval);
                    if(notification!=null){
                        //mTrace.setNotification(notification);
                    }
                }
            }

        }catch (Exception E){

        }
        MobSDK.init(this);
    }
    public static BaseApplication getInstance() {
        return instance;
    }

    private void initDebugTool() {
        if (!AppConfig.IS_DEBUG) {
            return;
        }
        /**crash????????????*/
        if (AppConfig.IS_DEBUG) {
            //CustomActivityOnCrash.install(this);
        }
        /**?????????LeakCanary */
    }


    private void initGalalleryFinal() {
        //????????????
        //ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarTextColor(Color.parseColor("#ffffff")).//???????????????????????????
                setTitleBarBgColor(Color.parseColor("#1a1819")).//?????????????????????
                setCheckSelectedColor(Color.parseColor("#3232CD")).//?????????????????????
                setFabNornalColor(Color.parseColor("#ff4545")).//??????floating??????nornal????????????
                setFabPressedColor(Color.parseColor("#C0D9D9")).
                        build();//??????floating??????pressed????????????
        //????????????
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)//??????????????????
                .setEnableEdit(true)//??????????????????
                .setEnableCrop(true)//??????????????????
                .setCropHeight(100)
                .setCropHeight(100)
                .setCropSquare(false)//???????????????
                .setRotateReplaceSource(true)
                .setCropReplaceSource(true)//??????????????????
                .setRotateReplaceSource(true)//??????????????????????????????
                .setForceCropEdit(true)
                .setForceCrop(false)//????????????????????????,???????????????????????????????????????????????????????????????????????????????????????????????????????????????
                .setEnablePreview(true)
                .setMutiSelectMaxSize(8)
                .build();
        //??????imageloader.setDebug(BuildConfig.DEBUG)??????Debug??????
        CoreConfig mCoreConfig = new CoreConfig.Builder(getApplicationContext(),
                new GlideImageLoader(), theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(mCoreConfig);
    }
    /***
     * ??????
     * ***/
    /**
     * ??????????????????
     */
    public void getCurrentLocation(OnEntityListener entityListener,
                                   OnTrackListener trackListener) {
        // ??????????????????????????????????????????????????????????????????????????????????????????????????????
        if(trackConf!=null){
            if (isNetworkAvailable(this)
                    && trackConf.contains("is_trace_started")
                    && trackConf.contains("is_gather_started")
                    && trackConf.getBoolean("is_trace_started", false)
                    && trackConf.getBoolean("is_gather_started", false)) {
                LatestPointRequest request = new LatestPointRequest(getTag(),
                        serviceId, entityName);
                ProcessOption processOption = new ProcessOption();
                processOption.setNeedDenoise(true);
                processOption.setRadiusThreshold(100);
                request.setProcessOption(processOption);
                mClient.queryLatestPoint(request, trackListener);
            } else {
                mClient.queryRealTimeLoc(locRequest, entityListener);
            }
        }
    }

    /**
     * ???????????????????????????
     *
     * @param request
     */
    public void initRequest(BaseRequest request) {
        request.setTag(getTag());
        request.setServiceId(serviceId);
    }
    /**
     * ??????????????????
     *
     * @return
     */
    public int getTag() {
        return mSequenceGenerator.incrementAndGet();
    }
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }
    public void goLoginActivity(){
        Intent intent=new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void initNavi() {
        if (BaiduNaviManagerFactory.getBaiduNaviManager().isInited()) {
            return;
        }
        BaiduNaviManagerFactory.getBaiduNaviManager().init(getApplicationContext(),
                getExternalFilesDir(null).getPath(),
                TAG, new IBaiduNaviManager.INaviInitListener() {

                    @Override
                    public void onAuthResult(int status, String msg) {
                        String result;
                        if (0 == status) {
                            result = "key????????????!";
                        } else {
                            result = "key????????????, " + msg;
                        }
                        Log.e(TAG, result);
                    }
                    @Override
                    public void initStart() {
                        Log.e(TAG, "initStart");

                    }
                    @Override
                    public void initSuccess() {
                        Log.e(TAG, "initSuccess");
                        // ?????????tts
                        initTTS();
                    }
                    @Override
                    public void initFailed(int errCode) {
                        Log.e(TAG, "initFailed-" + errCode);
                    }
                });
    }

    private void initTTS() {
        // ????????????TTS
        BNTTsInitConfig config = new BNTTsInitConfig.Builder()
                .context(getApplicationContext())
                .sdcardRootPath(getSdcardDir())
                .appFolderName(TAG)
                .appId("23535706")
                .appKey("GNoxrjuddXsF3cHNL0lYOnKO")
                .secretKey("FLOlotQO8tbuBeRFU1SuwUIDMYKHgGmP")
                .build();
        BaiduNaviManagerFactory.getTTSManager().initTTS(config);
    }
    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase
                (Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private void initNotification() {
        Notification.Builder builder = new Notification.Builder(this)
                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE);
        Intent notificationIntent = new Intent(this, DriverMainActivity.class);
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.ico_app);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // ??????PendingIntent
        builder.setContentIntent(PendingIntent.getActivity(this, 0, notificationIntent, 0))
                .setLargeIcon(icon)  // ??????????????????????????????(?????????)
                .setContentTitle("??????????????????") // ??????????????????????????????
                .setSmallIcon( R.drawable.ico_app) // ??????????????????????????????
                .setContentText("??????????????????...") // ?????????????????????
                .setWhen(System.currentTimeMillis());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && null != notificationManager) {
            NotificationChannel notificationChannel =
                    new NotificationChannel("trace", "trace_channel",
                            NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);

            builder.setChannelId("trace"); // Android O????????????????????????????????????channelId
        }
        notification = builder.build(); // ??????????????????Notification
        notification.defaults = Notification.FLAG_ONLY_ALERT_ONCE; //????????????????????????
        notificationManager.notify(1,notification);
    }


    private void  ff(){
        ForegroundNotification foregroundNotification = new
                ForegroundNotification("?????????","????????????????????????", R.drawable.ico_app,
                //???????????????????????????????????????
                new ForegroundNotificationClickListener() {
                    @Override
                    public void foregroundNotificationClick(Context context, Intent intent) {
                    }
                });

        KeepLive.startWork(this, KeepLive.RunMode.ENERGY, foregroundNotification, new KeepLiveService() {
            @Override
            public void onWorking() {
                Log.e("msg","??????work");
                int time = 60 * 1000 * 1;//30??????
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), RepetitionService.class);
                PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                if (Build.VERSION.SDK_INT < 19) {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, pendingIntent);
                } else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, pendingIntent);
                }
            }
            @Override
            public void onStop() {
                Log.e("msg","??????stop");

            }
        });

    }

    /**
     * startActivity with bundle
     * @param clazz
     * @param bundle
     */
    public void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

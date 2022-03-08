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
     *百度鹰眼
     * ***/
    private AtomicInteger mSequenceGenerator = new AtomicInteger();
    /**
     * 轨迹客户端
     */
    public LBSTraceClient mClient = null;
    /**
     * 轨迹服务
     */
    public Trace mTrace = null;
    public SharedPreferences trackConf = null;
    public LocRequest locRequest = null;
    /**
     * 轨迹服务ID
     */
    public long serviceId = 223521;
    /**
     * Entity标识
     */
    public String entityName = "车辆标识";

    public boolean isRegisterReceiver = false;
    /**
     * 服务是否开启标识
     */
    public boolean isTraceStarted = false;
    /**
     * 采集是否开启标识
     */
    public boolean isGatherStarted = false;
    public static int screenWidth = 0;
    public static int screenHeight = 0;

    // 动作活体条目集合
    public static List<LivenessTypeEnum> livenessList = new ArrayList<>();
    // 活体随机开关
    public static boolean isLivenessRandom = true;
    // 语音播报开关
    public static boolean isOpenSound = true;
    // 活体检测开关
    public static boolean isActionLive = true;
    // 质量等级（0：正常、1：宽松、2：严格、3：自定义）
    public static int qualityLevel = Constants.QUALITY_NORMAL;
    /****
     *开始
     * ***/
    @Override
    public void onCreate() {
        super.onCreate();
//        /**严苛模式主要检测两大问题，一个是线程策略，即TreadPolicy，另一个是VM策略，即VmPolicy。*/
        if (AppConfig.Relase && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().detectNetwork().
                    penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().
                    detectFileUriExposure().penaltyLog().build());
        }
        //初始化Hawk
        Hawk.init(getApplicationContext())
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSqliteStorage(getApplicationContext()))
                .setLogLevel(LogLevel.FULL)
                .build();
        //接口请求初始化
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
        /***  初始化galallfinal ***/
        initGalalleryFinal();
        if (BuildConfig.DEBUG) {
            initDebugTool();
        } else {// 仅release版本才检测更新和上传错误日志
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
                if(!entityName.equals("车辆标识")){
                    Log.e("msg","车辆标识="+entityName);
                    mClient = new LBSTraceClient(this);
                    mTrace = new Trace(serviceId, entityName);
                    trackConf = getSharedPreferences("track_conf", MODE_PRIVATE);
                    locRequest = new LocRequest(serviceId);
                    // 定位周期(单位:秒)
                    int gatherInterval = 10;
                    // 打包回传周期(单位:秒)
                    int packInterval = 60;
                    // 设置定位和打包周期
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
        /**crash异常捕获*/
        if (AppConfig.IS_DEBUG) {
            //CustomActivityOnCrash.install(this);
        }
        /**初始化LeakCanary */
    }


    private void initGalalleryFinal() {
        //设置主题
        //ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarTextColor(Color.parseColor("#ffffff")).//标题栏文本字体颜色
                setTitleBarBgColor(Color.parseColor("#1a1819")).//标题栏背景颜色
                setCheckSelectedColor(Color.parseColor("#3232CD")).//选择框选种颜色
                setFabNornalColor(Color.parseColor("#ff4545")).//设置floating按钮nornal状态颜色
                setFabPressedColor(Color.parseColor("#C0D9D9")).
                        build();//设置floating按钮pressed状态颜色
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)//开启相机功能
                .setEnableEdit(true)//开启编辑功能
                .setEnableCrop(true)//开启裁剪功能
                .setCropHeight(100)
                .setCropHeight(100)
                .setCropSquare(false)//裁剪正方形
                .setRotateReplaceSource(true)
                .setCropReplaceSource(true)//裁剪覆盖原图
                .setRotateReplaceSource(true)//选择时是否替换原图片
                .setForceCropEdit(true)
                .setForceCrop(false)//启动强制裁剪功能,一进入编辑页面就开启图片裁剪，不需要用户手动点击裁剪，此功能只针对单选操作
                .setEnablePreview(true)
                .setMutiSelectMaxSize(8)
                .build();
        //配置imageloader.setDebug(BuildConfig.DEBUG)设置Debug开关
        CoreConfig mCoreConfig = new CoreConfig.Builder(getApplicationContext(),
                new GlideImageLoader(), theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(mCoreConfig);
    }
    /***
     * 鹰眼
     * ***/
    /**
     * 获取当前位置
     */
    public void getCurrentLocation(OnEntityListener entityListener,
                                   OnTrackListener trackListener) {
        // 网络连接正常，开启服务及采集，则查询纠偏后实时位置；否则进行实时定位
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
     * 初始化请求公共参数
     *
     * @param request
     */
    public void initRequest(BaseRequest request) {
        request.setTag(getTag());
        request.setServiceId(serviceId);
    }
    /**
     * 获取请求标识
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
                            result = "key校验成功!";
                        } else {
                            result = "key校验失败, " + msg;
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
                        // 初始化tts
                        initTTS();
                    }
                    @Override
                    public void initFailed(int errCode) {
                        Log.e(TAG, "initFailed-" + errCode);
                    }
                });
    }

    private void initTTS() {
        // 使用内置TTS
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
        // 设置PendingIntent
        builder.setContentIntent(PendingIntent.getActivity(this, 0, notificationIntent, 0))
                .setLargeIcon(icon)  // 设置下拉列表中的图标(大图标)
                .setContentTitle("我找车司机端") // 设置下拉列表里的标题
                .setSmallIcon( R.drawable.ico_app) // 设置状态栏内的小图标
                .setContentText("服务正在运行...") // 设置上下文内容
                .setWhen(System.currentTimeMillis());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && null != notificationManager) {
            NotificationChannel notificationChannel =
                    new NotificationChannel("trace", "trace_channel",
                            NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);

            builder.setChannelId("trace"); // Android O版本之后需要设置该通知的channelId
        }
        notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.FLAG_ONLY_ALERT_ONCE; //设置为默认的声音
        notificationManager.notify(1,notification);
    }


    private void  ff(){
        ForegroundNotification foregroundNotification = new
                ForegroundNotification("我找车","我找车正在运行中", R.drawable.ico_app,
                //定义前台服务的通知点击事件
                new ForegroundNotificationClickListener() {
                    @Override
                    public void foregroundNotificationClick(Context context, Intent intent) {
                    }
                });

        KeepLive.startWork(this, KeepLive.RunMode.ENERGY, foregroundNotification, new KeepLiveService() {
            @Override
            public void onWorking() {
                Log.e("msg","保活work");
                int time = 60 * 1000 * 1;//30分钟
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
                Log.e("msg","保活stop");

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

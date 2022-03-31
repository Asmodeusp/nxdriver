package com.saimawzc.freight.base;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.entity.LocRequest;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.saimawzc.freight.ui.DriverMainActivity;
import com.saimawzc.freight.ui.MainActivity;
import com.saimawzc.freight.ui.login.LoginActivity;
import com.saimawzc.freight.weight.BottomDialogUtil;
import com.saimawzc.freight.weight.ToastCompat;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.dto.login.AreaDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.weight.utils.DateUtils;
import com.saimawzc.freight.weight.utils.FileUtil;
import com.saimawzc.freight.weight.utils.api.auto.AuthApi;
import com.saimawzc.freight.weight.utils.Md5Utils;
import com.saimawzc.freight.weight.utils.MyComparator;
import com.saimawzc.freight.weight.utils.SdCardUtil;
import com.saimawzc.freight.weight.utils.api.mine.MineApi;
import com.saimawzc.freight.weight.utils.api.order.OrderApi;
import com.saimawzc.freight.weight.utils.api.tms.TmsApi;
import com.saimawzc.freight.weight.utils.app.AppManager;
import com.saimawzc.freight.weight.utils.dialog.DialogLoading;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.keeplive.jobhander.JobHandlerService;
import com.saimawzc.freight.weight.utils.keeplive.onepixe.ScreenService;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.saimawzc.freight.weight.utils.serves.SingASongService;
import com.saimawzc.freight.weight.utils.statusbar.StatusBarUtil;
import com.saimawzc.freight.weight.utils.update.InstallUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.nanchen.compresshelper.CompressHelper;
import com.werb.permissionschecker.PermissionChecker;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;

import static com.baidu.navisdk.ui.util.TipTool.toast;
import static com.saimawzc.freight.ui.my.identification.DriverCarrierFragment.REQUEST_CODE_PIC;

/**
 * Created by Administrator on 2018-03-21.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected BaseActivity context;
    public Context mContext;
    private DialogLoading loading;
    public int screenWidth = 0;
    public int screenHeight = 0;
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,

    };
    private boolean mNeedOnBus;
    public static final String[] PERMISSIONS_CAMERA = new String[]{
            Manifest.permission.CAMERA,
    };
    public static final String[] PERMISSIONSS_LOCATION = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    public PermissionChecker permissionChecker;
    public UserInfoDto userInfoDto;
    public AuthApi authApi = Http.http.createApi(AuthApi.class);
    public MineApi mineApi = Http.http.createApi(MineApi.class);
    public OrderApi orderApi = Http.http.createApi(OrderApi.class);
    public TmsApi tmsApi = Http.http.createApi(TmsApi.class);
    public InstallUtils.DownloadCallBack downloadCallBack;

    protected void setNeedOnBus(boolean needOnBus) {
        mNeedOnBus = needOnBus;
    }

    public static String apkURLQ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁用横屏
        if (getViewId() != 0) {
            setContentView(getViewId());
        }
        ButterKnife.bind(this);
        context = this;
        mContext = this;
        loading = new DialogLoading(this);
        AppManager.get().addActivity(this);
        onGetBundle(getIntent().getExtras());
        init();
        initListener();
        //初始化沉浸式
        initImmersionBar();

        if (mNeedOnBus) {
            EventBus.getDefault().register(this);
        }
    }




    protected void setDownApkUrl() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            apkURLQ = getExternalFilesDir("Caches").getAbsolutePath() + "/nxdriver/" + BaseActivity.getCurrentTime("yyyy-MM-dd HH:mm") + "siji.apk";
        }
    }

    public void setStatusBar(boolean isUseBlackFontWithStatusBar, boolean isUseFullScreenMode, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isUseFullScreenMode) {//是否设置成透明状态栏，即就是全屏模式
                StatusBarUtil.transparencyBar(this);
            } else {
                StatusBarUtil.setStatusBarColor(this, color);
            }
            if (isUseBlackFontWithStatusBar) {//状态栏改变状态栏颜色为黑色
                StatusBarUtil.setLightStatusBar(this, true, isUseFullScreenMode);
            }
        }
    }

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.bg));
        }
        //设置共同沉浸式样式
        ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).
                statusBarDarkFont(true).
                statusBarColor(R.color.bg).
                navigationBarColor(R.color.bg).
                init();
    }

    public BaseActivity() {
    }

    protected abstract int getViewId();

    protected abstract void init();

    protected abstract void initListener();

    protected abstract void onGetBundle(Bundle bundle);

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    /**
     * 是否字符串为空
     **/
    public boolean isEmptyStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmptyStr(TextView view) {
        if (TextUtils.isEmpty(view.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmptyStr(EditText view) {
        if (TextUtils.isEmpty(view.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 提示框
     **/
    public void showMessage(Object message) {
        if ("null".equals(message) || message == null) {
            message = "数据异常";
        }
        if (isNotificationEnabled(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                showMessage7(message + "");
            } else {
                showToast(message);
            }
        } else {
            myToast(message + "");

        }
    }

    /**
     * 提示框
     **/
    public void showMessage7(String message) {
        if (message == null || "null".equals(message)) {
            message = "数据异常";
        }
        try {
            if (context != null && !isDestroy(this)) {
                ToastCompat toastCompat = new ToastCompat();
                toastCompat.showToast(this.getApplicationContext(), message, Toast.LENGTH_SHORT);
            }
        } catch (Exception e) {

        }
        // showToast(message);
    }

    private void showToast(final Object message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (context != null && !isDestroy(context)) {
                    try {
                        Toast toast = Toast.makeText(context.getApplicationContext(), message + "", Toast.LENGTH_SHORT);
                        // 重点是这个方法：显示的方位及距离的偏差
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } catch (Exception e) {
                    }

                } else {
                }
            }
        });
    }

    /**
     * 本地上传图片
     **/
    public String tempImage;
    public final int REQUEST_CODE_CAMERA = 1000;

    public void showCameraAction(Context c) {
        tempImage = SdCardUtil.getCacheTempImage(context);
        openCamera(tempImage, REQUEST_CODE_CAMERA);
    }

    public void openCamera(String path, int code) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("return-data", false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        //Uri mImageUri = FileProvider.getUriForFile(this,FILE_PROVIDER_AUTHORITY,imageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.parse("file:///" + path));
        startActivityForResult(intent, code);
    }


    public void setToolbar(Toolbar toolbar, String title) {
        toolbar.setNavigationIcon(R.drawable.ico_menu_return);
        TextView titleTv = (TextView) toolbar.findViewById(R.id.title);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        if (titleTv != null) {
            titleTv.setText(title);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param
     */
    public void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /***
     * 获取签名
     * */
    public static String getMd5Sign(String accessKey, Map<String, Object> params) {
        Set<String> set = params.keySet();
        String[] keys = new String[set.size()];
        set.toArray(keys);
        Arrays.sort(keys, new MyComparator());

        StringBuffer signVal = new StringBuffer();
        for (String key : keys) {
            String value = params.get(key) + "";
            if (Md5Utils.isEmpty(value))
                Log.i("TAG", key + "++" + value + "..");
            signVal.append(key).append(value);
        }
        Log.i("msg", signVal.toString() + accessKey);
        String sign = Md5Utils.getMD5Str(signVal + accessKey, "utf-8");
        Log.i("msg", sign);
        return sign;
    }


    /***
     * 是否登录
     * ***/
    public boolean isLogin() {
        if (TextUtils.isEmpty(Hawk.get(PreferenceKey.ID) + "")) {
            return false;
        } else if ((Hawk.get(PreferenceKey.ID) + "").equalsIgnoreCase("null")) {
            return false;
        } else {
            return true;
        }
    }

    /****
     * 判断转换结果是不是String
     * **/
    public boolean changeOk(String key) {
        if (Hawk.get(key) instanceof String) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    public void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    public void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 显示加载弹窗
     */
    public void showLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loading == null) {
                    loading = new DialogLoading(context);
                }
                if (!isDestroy(context) && !loading.isShowing()) {
                    loading.setDialogLabel("");
                    loading.show();
                }
            }
        });
    }

    public void showLoadingDialog(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isDestroy(BaseActivity.this) && !loading.isShowing()) {
                    loading.setDialogLabel(txt);
                    loading.setCancelable(false);
                    loading.show();
                }
            }
        });
    }

    /**
     * 关闭加载弹窗
     */
    public void dismissLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loading != null) {
                    loading.dismiss();
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (loading != null) {
            loading.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissLoadingDialog();
        if (mNeedOnBus) {
            EventBus.getDefault().unregister(this);
        }
        AppManager.get().removeActivity(this);
        if (processhandler != null) {
            processhandler.removeCallbacksAndMessages(null);
        }
        if (bottomDialogUtil != null) {
            bottomDialogUtil.dismiss();
        }
        super.onDestroy();
    }

    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {

        }
        return versionCode;
    }

    public static String getVersionName(Context context) {
        String versionCode = "";
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
        }
        return versionCode;
    }

    /**
     * 是否有网络连接
     ***/
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    private final int MIN_DELAY_TIME = 100;  // 两次点击间隔不能少于1000ms
    private long lastClickTime;

    /****
     * 防止重复点击
     * */
    public boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }


    /***
     * 显示Log
     * ****/
    public void showLog(String tag, String contect) {
        Log.e(tag, contect);
    }


    public void initPush() {
        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                "kcKPgo9GQKZo7PvKOGjurEOTGPoPBtyr");
    }

    /**
     * 以license文件方式初始化
     */
    public void initAccessToken() {
        OCR.getInstance(this).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                Log.e("msg", "百度云初始化" + token);
                CameraNativeHelper.init(BaseActivity.this, OCR.getInstance(BaseActivity.this).getLicense(),
                        new CameraNativeHelper.CameraNativeInitCallback() {
                            @Override
                            public void onError(int errorCode, Throwable e) {
                                String msg;
                                switch (errorCode) {
                                    case CameraView.NATIVE_SOLOAD_FAIL:
                                        msg = "加载so失败，请确保apk中存在ui部分的so";
                                        break;
                                    case CameraView.NATIVE_AUTH_FAIL:
                                        msg = "授权本地质量控制token获取失败";
                                        break;
                                    case CameraView.NATIVE_INIT_FAIL:
                                        msg = "本地质量控制";
                                        break;
                                    default:
                                        msg = String.valueOf(errorCode);
                                }
                                Log.e("msg", "本地质量控制初始化错误，错误原因： " + msg);
                            }
                        });

            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                Log.e("msg", "licence方式获取token失败" + error.getMessage());
                initAccessTokenWithAkSk();
            }
        }, getApplicationContext());


    }

    /**
     * 用明文ak，sk初始化
     */
    private void initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                Log.e("msg", "百度云初始化" + token);
                //  初始化本地质量控制模型,释放代码在onDestory中
                //  调用身份证扫描必须加上 intent.putExtra(OcrCameraActivity.KEY_NATIVE_MANUAL, true); 关闭自动初始化和释放本地模型
                CameraNativeHelper.init(BaseActivity.this, OCR.getInstance(BaseActivity.this).getLicense(),
                        new CameraNativeHelper.CameraNativeInitCallback() {
                            @Override
                            public void onError(int errorCode, Throwable e) {
                                String msg;
                                switch (errorCode) {
                                    case CameraView.NATIVE_SOLOAD_FAIL:
                                        msg = "加载so失败，请确保apk中存在ui部分的so";
                                        break;
                                    case CameraView.NATIVE_AUTH_FAIL:
                                        msg = "授权本地质量控制token获取失败";
                                        break;
                                    case CameraView.NATIVE_INIT_FAIL:
                                        msg = "本地质量控制";
                                        break;
                                    default:
                                        msg = String.valueOf(errorCode);
                                }
                                Log.e("msg", "本地质量控制初始化错误，错误原因： " + msg);
                            }
                        });

            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                Log.e("msg", "AK，SK方式获取token失败" + error.getMessage());
            }
        }, getApplicationContext(), "GNoxrjuddXsF3cHNL0lYOnKO", "FLOlotQO8tbuBeRFU1SuwUIDMYKHgGmP");
    }

    /**
     * 判断文件大小处于限制内
     *
     * @param fileLen  文件长度
     * @param fileSize 限制大小
     * @param fileUnit 限制的单位（B,K,M,G）
     * @return
     */
    public static boolean checkFileSizeIsLimit(Long fileLen, int fileSize, String fileUnit) {
//        long len = file.length();
        double fileSizeCom = 0;
        if ("B".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen;
        } else if ("K".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / 1024;
        } else if ("M".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / (1024 * 1024);
        } else if ("G".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / (1024 * 1024 * 1024);
        }
        if (fileSizeCom > fileSize) {
            return false;
        }
        return true;

    }

    /****
     * 压缩
     * **/
    public static File compress(final Context context, final File file) {

        File tempFile = null;
        if (file == null) {
            return null;
        }
        //Log.e("msg","图片压缩前"+file.length()/(1024*1024)+"M");
        if (!checkFileSizeIsLimit(file.length(), 800, "K")) {//图片大于1m
            tempFile = new CompressHelper.Builder(context)
                    //.setMaxWidth(720)   //默认最大宽度为720
                    //   .setMaxHeight(960)  //默认最大高度为960
                    .setQuality(80)     //默认压缩质量为80
                    .setCompressFormat(Bitmap.CompressFormat.JPEG) //设置默认压缩为jpg格式
                    .setFileName(System.currentTimeMillis() + "") //设置你的文件名
                    .build()
                    .compressToFile(file);
            return tempFile;

        } else {
            return file;
        }
    }


    /****
     * 缓存地域
     * **/
    public void cacheArae() {
        mineApi.getArea().enqueue(new CallBack<List<AreaDto>>() {
            @Override
            public void success(List<AreaDto> response) {
                Hawk.put(PreferenceKey.CITY_INFO, response);
            }

            @Override
            public void fail(String code, String message) {
            }
        });
    }

    public void initpermissionChecker() {
        permissionChecker = new PermissionChecker(context);
        permissionChecker.setTitle(getString(R.string.check_info_title));
        permissionChecker.setMessage(getString(R.string.check_info_message));
    }

    public void stopSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    //判断权限
    public boolean commonROMPermissionCheck(Context context) {
        Boolean result = true;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class clazz = Settings.class;
                Method canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
                Settings.canDrawOverlays(context);
                result = (Boolean) canDrawOverlays.invoke(null, context);
            } catch (Exception e) {

            }
        }
        return result;
    }

    //申请权限
    public void requestAlertWindowPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 111);
    }


    /**
     * 检查包是否存在
     *
     * @param packname
     * @return
     */
    public boolean checkPackInfo(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }


    private String apkDownloadPath;
    private ProgressDialog progressDialog;
    private Handler processhandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (!isDestroy(BaseActivity.this)) {
                if (msg.what <= 100) {
                    if (progressDialog != null) {
                        progressDialog.setProgress(msg.what);
                    }
                } else {
                    if (progressDialog != null) {
                        progressDialog.setMessage("由于网络原因，当前下载失败，请稍后再试");
                        progressDialog.dismiss();
                        turnToMain();
                    }
                }
            }
        }
    };

    /***
     * 版本更新
     * **/
    public void initCallBack() {

        downloadCallBack = new InstallUtils.DownloadCallBack() {
            @Override
            public void onStart() {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(context);
                }
                progressDialog.setTitle("提示");
                progressDialog.setMessage("我找车司机正在下载中");
                // 设置ProgressDialog 标题图标
                progressDialog.setIcon(R.drawable.ico_app);

                // 设置ProgressDialog 的进度条是否不明确
                progressDialog.setIndeterminate(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
                // 设置ProgressDialog 是否可以按退回按键取消
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
                progressDialog.setMax(100);
                progressDialog.show();
            }

            @Override
            public void onComplete(String path) {
                apkDownloadPath = path;
                Hawk.put(PreferenceKey.OLD_UPDATE_TIME, apkDownloadPath);
                if (progressDialog != null) {
                    if (context == null || context.isDestroy(context)) {
                    } else {
                        progressDialog.dismiss();
                    }
                }
                //先判断有没有安装权限
                InstallUtils.checkInstallPermission(context, new InstallUtils.InstallPermissionCallBack() {
                    @Override
                    public void onGranted() {
                        //去安装APK
                        installApk(apkDownloadPath);
                    }

                    @Override
                    public void onDenied() {
                        //弹出弹框提醒用户
                        AlertDialog alertDialog = new AlertDialog.Builder(context)
                                .setTitle("温馨提示")
                                .setMessage("必须授权才能安装APK，请设置允许安装")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //打开设置页面
                                        InstallUtils.openInstallPermissionSetting(context, new InstallUtils.InstallPermissionCallBack() {
                                            @Override
                                            public void onGranted() {
                                                //去安装APK
                                                installApk(apkDownloadPath);
                                            }

                                            @Override
                                            public void onDenied() {
                                                //还是不允许咋搞？
                                                Toast.makeText(context, "不允许安装咋搞？强制更新就退出应用程序吧！", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                })
                                .create();
                        alertDialog.show();
                    }
                });
            }

            @Override
            public void onLoading(long total, long current) {
                //内部做了处理，onLoading 进度转回progress必须是+1，防止频率过快
                int progress = (int) (current * 100 / total);
                Log.e("msg", "下载" + progress);
                Message message = new Message();
                message.what = progress;
                processhandler.sendMessage(message);
            }

            @Override
            public void onFail(Exception e) {
                Log.e("msg", "下载失败" + e.toString());
                showMessage("由于网络原因，当前下载失败，请稍后再试" + e.getMessage());
                if (progressDialog != null) {
                    Message message = new Message();
                    message.what = 1001;
                    processhandler.sendMessage(message);
                }
            }

            @Override
            public void cancle() {
            }
        };
    }

    protected void installApk(String path) {
        Hawk.put(PreferenceKey.OLD_UPDATE_TIME, "");
        InstallUtils.installAPK(context, path, new InstallUtils.InstallCallBack() {
            @Override
            public void onSuccess() {
                //onSuccess：表示系统的安装界面被打开
                //防止用户取消安装，在这里可以关闭当前应用，以免出现安装被取消
                Toast.makeText(context, "正在安装程序", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Exception e) {
                showMessage("安装失败:" + e.toString());
            }
        });
    }

    public String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public void callPhone(String phoneNum) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            context.showMessage("未获取到电话权限");

            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    // api_key 绑定
    public void initWithApiKey() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    PushManager.startWork(getApplicationContext(),
                            PushConstants.LOGIN_TYPE_API_KEY,
                            "1mQBmIMOFCuns7iwjszzsaeZ");
                } catch (Exception e) {

                }
            }
        });


    }

    // 解绑
    public void unBindForApp() {
        // Push：解绑
        PushManager.stopWork(getApplicationContext());
    }

    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s, String fromart) {
        String res;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fromart);
            Date date = simpleDateFormat.parse(s);
            long ts = date.getTime();
            return ts / 1000;
        } catch (Exception e) {
            return 0;
        }
    }

    /****
     * 获取当前时间
     * ***/
    public static String getCurrentTime(String timeformat) {//"yyyy-MM-dd HH:mm"
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeformat);// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date) + "";
    }

    /****
     * 时间戳转换成日期
     * **/
    public static String transTime(String time, String trans) {

        try {
            if (time.contains("-")) {
                time = dateToStamp(time, "yyyy-MM-dd HH:mm:ss") + "";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(trans);
            String sd = sdf.format(new Date(Long.parseLong(String.valueOf(time)) * 1000L));
            return sd;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getWeekOfDate(String timestamp) {
        try {
            if (timestamp.contains("-")) {
                timestamp = dateToStamp(timestamp, "yyyy-MM-dd HH:mm:ss") + "";
            }
            String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(Long.parseLong(String.valueOf(timestamp)) * 1000L));
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0)
                w = 0;
            return weekDays[w];
        } catch (Exception e) {
            return "";
        }
    }

    /****
     * 获取时间推迟一周
     * ***/
    public static String delayWeek(String timeformat) {//"yyyy-MM-dd HH:mm"
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeformat);// HH:mm:ss
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.DAY_OF_MONTH, curr.get(Calendar.DAY_OF_MONTH) - 7);
        Date date = curr.getTime();
        return simpleDateFormat.format(date) + "";
    }

    /****
     * 获取时间推迟月
     * ***/
    public static String delayMonth(String timeformat, int deaymonth) {//"yyyy-MM-dd HH:mm"
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeformat);// HH:mm:ss
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.MONTH, curr.get(Calendar.MONTH) - deaymonth);
        Date date = curr.getTime();
        return simpleDateFormat.format(date) + "";
    }

    /****
     * 转换日期格式
     * **/
    public static String trantTime(String oldtime, String oldtimeformart, String newTimeformart) {
        Date date = null;
        try {
            SimpleDateFormat oldDateFormat = new SimpleDateFormat(oldtimeformart);//
            SimpleDateFormat newDateFormat = new SimpleDateFormat(newTimeformart);
            date = oldDateFormat.parse(oldtime);
            if (date != null) {
                return newDateFormat.format(date) + "";
            } else {
                return "";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 检查通知栏权限有没有开启
     */
    public static boolean isNotificationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).areNotificationsEnabled();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            ApplicationInfo appInfo = context.getApplicationInfo();
            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;

            try {
                Class<?> appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
                int value = (Integer) opPostNotificationValue.get(Integer.class);
                return (Integer) checkOpNoThrowMethod.invoke(appOps, value, uid, pkg) == 0;
            } catch (NoSuchMethodException | NoSuchFieldException | InvocationTargetException | IllegalAccessException | RuntimeException | ClassNotFoundException ignored) {
                return true;
            }
        } else {
            return true;
        }
    }

    BottomDialogUtil bottomDialogUtil;

    private void myToast(String message) {
        if (bottomDialogUtil == null) {
            bottomDialogUtil = new BottomDialogUtil.Builder()
                    .setContext(context) //设置 context
                    .setContentView(R.layout.toast) //设置布局文件
                    .setOutSideCancel(false) //设置点击外部取消
                    .builder()
                    .show();
        }
        TextView tvtoast = (TextView) bottomDialogUtil.getItemView(R.id.tvtoast);
        tvtoast.setText(message);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                try {
                    if (bottomDialogUtil != null && !isDestroy(BaseActivity.this)) {
                        bottomDialogUtil.dismiss();
                        bottomDialogUtil = null;
                    }
                } catch (Exception e) {

                } finally {
                    if (bottomDialogUtil != null && !isDestroy(BaseActivity.this)) {
                        bottomDialogUtil.dismiss();
                    }
                    bottomDialogUtil = null;
                }
            }
        }, 1000);

    }

    public UserInfoDto getUserInfoDto(UserInfoDto useDto) {
        if (useDto == null || TextUtils.isEmpty(useDto.getRoleId())) {
            useDto = Hawk.get(PreferenceKey.USER_INFO);
            return useDto;
        } else {
            return useDto;
        }

    }

    public boolean isContainTemp(String str) {

        String reg = "^[^\\s]{6,16}$";
        if (str.matches(reg)) {
            return false;
        }
        return true;
    }

    private void turnToMain() {
        userInfoDto = Hawk.get(PreferenceKey.USER_INFO);
        if (!TextUtils.isEmpty(Hawk.get(PreferenceKey.ID, ""))) {//已经登录
            if (userInfoDto != null) {
                if (userInfoDto.getRole() == 2) {
                    readyGo(MainActivity.class);
                } else if (userInfoDto.getRole() == 3) {
                    readyGo(DriverMainActivity.class);
                }
            }
        } else {
            readyGo(LoginActivity.class);
        }
    }

    protected void initCamera(String type) {
        if (mContext == null) {
            return;
        }
        tempImage = System.currentTimeMillis() + "";
        Intent intent = new Intent(mContext, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(mContext, tempImage).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                true);
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                type);
        startActivityForResult(intent, REQUEST_CODE_PIC);
    }

    public static String transTimehenggang(String time, String timeend) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

        Date beginDate = null;
        try {
            beginDate = sdf.parse(time);
            SimpleDateFormat df = new SimpleDateFormat(timeend);
            //format参数是日期格式的对象格式成上述形式
            return df.format(beginDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void showCameraAction() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (context == null) {
            if (takePictureIntent.resolveActivity(BaseApplication.getInstance().getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                }
                if (takePictureIntent == null) {
                    return;
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(BaseApplication.getInstance(),
                            "com.saimawzc.freight.updateFileProvider",
                            photoFile);
                    if (photoURI == null) {
                        return;
                    }
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
                }
            }
        } else {
            if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                }
                if (takePictureIntent == null) {
                    return;
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(context,
                            "com.saimawzc.freight.updateFileProvider",
                            photoFile);
                    if (photoURI == null) {
                        return;
                    }
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
                }
            }
        }

    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir;

        if (context != null) {
            storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        } else {
            storageDir = BaseApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        tempImage = image.getAbsolutePath();
        return image;
    }

    protected void initTrack(BaseApplication trackApp) {
        if (userInfoDto == null) {
            userInfoDto = Hawk.get(PreferenceKey.USER_INFO);
        }
        if (userInfoDto != null) {
            if (TextUtils.isEmpty(userInfoDto.getAuthState())) {
                return;
            }
            if (userInfoDto.getAuthState().equals("1")) {
                String entityName = userInfoDto.getRoleId();
                if (TextUtils.isEmpty(entityName)) {
                    showMessage("登录信息有误，请重新登录");
                    Hawk.put(PreferenceKey.ID, "");
                    Hawk.put(PreferenceKey.DRIVER_IS_INDENFICATION, "");
                    Hawk.put(PreferenceKey.LOGIN_TYPE, "");
                    Hawk.put(PreferenceKey.USER_INFO, null);
                    Hawk.put(PreferenceKey.PERSON_CENTER, null);
                    readyGo(LoginActivity.class);
                    return;
                }
                if (trackApp == null) {
                    return;
                }
                Log.e("msg", "entityName=" + entityName);
                trackApp.mClient = new LBSTraceClient(trackApp);
                trackApp.mTrace = new Trace(trackApp.serviceId, entityName);
                trackApp.locRequest = new LocRequest(trackApp.serviceId);
                // 定位周期(单位:秒)
                int gatherInterval = 10;
                // 打包回传周期(单位:秒)
                int packInterval = 60;
                // 设置定位和打包周期
                trackApp.mClient.setInterval(gatherInterval, packInterval);
                Log.e("msg", "注册完毕");
            }
        }
    }

    protected void saveLife() {
        /***无声音乐保活***/
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent intent = new Intent(this,
                        SingASongService.class);
                startForegroundService(intent);
            } else {
                startService(new Intent(this,
                        SingASongService.class));
            }
        } catch (Exception e) {
        }
        /***一像素保活***/
        Intent intent = new Intent(this, ScreenService.class);
        startService(intent);
        /****  JobHandlerService拉活**/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            JobHandlerService.startJob(this);
        }
    }

    public boolean isEmptyUserInfo(UserInfoDto userInfoDto) {
        if (userInfoDto == null) {
            return true;
        }
        if (TextUtils.isEmpty(userInfoDto.getRoleId())) {
            return true;
        }
        return false;
    }

    protected int getGapMinutes(String startDate, String endDate) {
        long start = 0;
        long end = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            start = df.parse(startDate).getTime();

            end = df.parse(endDate).getTime();

        } catch (Exception e) {
        }
        int minutes = (int) ((end - start) / (1000 * 60));
        return minutes;
    }

    /**
     * 判断Activity是否Destroy
     *
     * @param mActivity
     * @return true:已销毁
     */
    public static boolean isDestroy(Activity mActivity) {
        if (mActivity == null ||
                mActivity.isFinishing() ||
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }


}

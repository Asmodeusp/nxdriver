package com.saimawzc.freight.ui.my.set;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.ui.WebViewActivity;
import com.saimawzc.freight.weight.utils.trace.TraceUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.service.notification.Condition.SCHEME;
import static com.baidu.navisdk.util.jar.JarUtils.getPackageName;

/***保活设置***/
public class keepLiveSetFragment  extends BaseFragment {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tvopenxf) TextView tvXf;
    @BindView(R.id.tvopsd)TextView tvSd;
    @BindView(R.id.tvTraceStatus)TextView tvStatus;
    TraceUtils  utils;

    @Override
    public int initContentView() {
        return R.layout.fragment_set_keeplive;
    }


    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"保活设置");
        if(utils==null){
            utils =new TraceUtils((BaseApplication)
                    mContext.getApplicationContext(),context);
        }
        if(utils!=null){
            utils.startSercive();
            utils.startRealTimeLoc(30);
            utils.setOnChangeListener(new TraceUtils.StateChangeListener() {
                @Override
                public void StateChange(String State) {
                    Log.e("msg","开启鹰眼状态"+State);
                    if(TextUtils.isEmpty(State)){
                        tvStatus.setText("鹰眼尚未开启");
                        tvStatus.setTextColor(mContext.getResources().getColor(R.color.red));
                    }else {
                        if(State.contains("成功")){
                            tvStatus.setText("鹰眼尚未开启");
                            tvStatus.setTextColor(mContext.getResources().getColor(R.color.red));
                        }else if(State.equals("服务已开启")){
                            tvStatus.setText("鹰眼正在持续运行中");
                        }else {
                            tvStatus.setText(State);
                            tvStatus.setTextColor(mContext.getResources().getColor(R.color.red));
                        }
                    }
                }
            });
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        if(checkFloatPermission()){
            tvXf.setText("已开启悬浮窗权限");
            tvXf.setTextColor(mContext.getResources().getColor(R.color.white));
        }else {
            tvXf.setText("请开启悬浮框权限");
            tvXf.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        if(isIgnoringBatteryOptimizations()){
            tvSd.setText("已开启电池优化");
            tvSd.setTextColor(mContext.getResources().getColor(R.color.white));
        }else {
            tvSd.setText("请开启电池优化");
            tvSd.setTextColor(mContext.getResources().getColor(R.color.red));
        }
    }

    @Override
    public void initData() {

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.tvopenxf,R.id.tvopsd,R.id.tvdaohang,R.id.tvTraceStatus})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvopenxf:
                if(tvXf.getText().toString().equals("请开启悬浮框权限")){
                    requestAlertWindowPermission();
                }
                break;
            case R.id.tvopsd:
                if(tvSd.getText().toString().equals("请开启电池优化")){
                    requestIgnoreBatteryOptimizations();
                }
                break;
            case R.id.tvdaohang:
                WebViewActivity.loadUrl(context, "后台设置","https://www.wzcwlw.com/helper/");
                break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkFloatPermission() {
        Log.e("SDK_INT", Build.VERSION.SDK_INT + "");
        //如果当期SDK版本大于21，也就是6.0及以上的话，进行权限判断，如果是6.0以下版本直接添加悬浮窗
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //判断是否可以进行全局绘制，及悬浮窗是否有开启
            if (Settings.canDrawOverlays(mContext)) {
                return true;
            } else {
               return false;
            }
        } else {
            return true;
        }
    }

    //申请权限
    public void requestAlertWindowPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 111);
    }
    /****判断应用是否在白名单中**/
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isIgnoringBatteryOptimizations() {
        boolean isIgnoring = false;
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if(powerManager != null) {
            try {
                isIgnoring = powerManager.isIgnoringBatteryOptimizations(getPackageName());
            }catch (Exception e){
            }
        }
        return isIgnoring;
    }
    /****
     * 申请加入后台应用白名单
     * **/
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestIgnoreBatteryOptimizations() {
        try{
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:"+ getPackageName()));
            startActivity(intent);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(utils!=null){
            utils.stateChangeListener=null;
            utils=null;
        }
    }
}

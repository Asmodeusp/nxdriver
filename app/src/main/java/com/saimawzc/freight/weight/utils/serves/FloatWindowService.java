package com.saimawzc.freight.weight.utils.serves;


import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.saimawzc.freight.R;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.ui.DriverMainActivity;
import com.saimawzc.freight.ui.MainActivity;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.saimawzc.freight.weight.utils.serves.floatview.AssistMenuWindowManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



public class FloatWindowService extends Service {

    /**
     * 用于在线程中创建或移除悬浮窗。
     */
    private Handler handler = new Handler();

    /**
     * 定时器，定时进行检测当前应该创建还是移除悬浮窗。
     */
    private Timer timer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 开启定时器，每隔0.5秒刷新一次
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);

        }
       // acquireWakeLock(getApplicationContext());
        setNofification();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Service被终止的同时也停止定时器继续运行
        timer.cancel();
        timer = null;
       // stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知

    }

    private void setNofification(){
        Notification.Builder builder = new Notification.Builder
                (this.getApplicationContext()); //获取一个Notification构造器
        UserInfoDto userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
        Intent nfIntent=null;
        if(userInfoDto!=null){
            if(userInfoDto.getRole()==2){
                nfIntent= new Intent(this, MainActivity.class);
            } else if(userInfoDto.getRole()==3){
                 nfIntent = new Intent(this, DriverMainActivity.class);
            }
        }else {
            nfIntent = new Intent(this, DriverMainActivity.class);
        }

        builder.setContentIntent(PendingIntent.
                getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.drawable.ico_app)) // 设置下拉列表中的图标(大图标)
                .setContentTitle("宁夏司机端正在运行") // 设置下拉列表里的标题
                .setSmallIcon(R.drawable.ico_app) // 设置状态栏内的小图标
                .setContentText("宁夏司机端") // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间

        Notification notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        startForeground(110, notification);
    }


    class RefreshTask extends TimerTask {

        @Override
        public void run() {
            // 当前界面是桌面，且没有悬浮窗显示，则创建悬浮窗。
            if (isHome() && !AssistMenuWindowManager.isWindowShowing()) {
                Log.e("msg","是桌面，没有悬浮框");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        AssistMenuWindowManager.createSmallWindow(getApplicationContext());
                    }
                });
            }
            // 当前界面不是桌面，且有悬浮窗显示，则移除悬浮窗。
            else if (!isHome() && AssistMenuWindowManager.isWindowShowing()) {
                Log.e("msg","不是桌面，有悬浮框");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        AssistMenuWindowManager.removeSmallWindow(getApplicationContext());
                        AssistMenuWindowManager.removeBigWindow(getApplicationContext());
                    }
                });
            }
            // 当前界面是桌面，且有悬浮窗显示，则更新内存数据。
            else if (isHome() && AssistMenuWindowManager.isWindowShowing()) {
                Log.e("msg","不是桌面有悬浮框");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        AssistMenuWindowManager.updateUsedPercent(getApplicationContext());
                    }
                });
            }
        }

    }

    /**
     * 判断当前界面是否是桌面
     */
    private boolean isHome() {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        return getHomes().contains(rti.get(0).topActivity.getPackageName());
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    private List<String> getHomes() {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }

    PowerManager.WakeLock mWakeLock;

    //申请设备电源锁
    public  void acquireWakeLock(Context context)
    {
        if (null == mWakeLock)
        {
            PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK|PowerManager.ON_AFTER_RELEASE, "WakeLock");
            if (null != mWakeLock)
            {
                mWakeLock.acquire();
            }
        }
    }
    //释放设备电源锁
    public  void releaseWakeLock()
    {
        if (null != mWakeLock)
        {
            mWakeLock.release();
            mWakeLock = null;
        }

    }


}

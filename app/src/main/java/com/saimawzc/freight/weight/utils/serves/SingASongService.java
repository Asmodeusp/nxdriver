package com.saimawzc.freight.weight.utils.serves;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseApplication;

public class SingASongService  extends Service {
    private MediaPlayer mMediaPlayer;
    private Thread thread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // 注意notification也要适配Android 8 哦
            createNotificationChannel();
        }
        MyThread myThread = new MyThread();
        if(thread==null){
            thread = new Thread(myThread);
        }
        mMediaPlayer = MediaPlayer.create(BaseApplication.getInstance(), R.raw.no_kill);
        mMediaPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // 注意notification也要适配Android 8 哦
            createNotificationChannel();
        }
        if(thread!=null){
            if(!thread.isAlive()){
                try{
                    thread.start();
                }catch (IllegalThreadStateException E){
                }
            }
        }
        return START_STICKY;
    }

    //开始、暂停播放
    private void startPlaySong() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(BaseApplication.getInstance(), R.raw.no_kill);
            mMediaPlayer.start();
        } else {
            if(!mMediaPlayer.isPlaying()){
                mMediaPlayer.start();
            }
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            if(mMediaPlayer!=null){
                mMediaPlayer.pause();
                stopPlaySong();
            }
            if(thread!=null){
                thread.stop();
            }
        }catch (Exception e){
        }
        try{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                // 注意notification也要适配Android 8 哦
                createNotificationChannel();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(new Intent(getApplicationContext(), SingASongService.class));
            } else {
                startService(new Intent(getApplicationContext(), SingASongService.class));
            }
        }catch(Exception  e){

        }

    }



    //停止播放销毁对象
    private void stopPlaySong() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
    class MyThread implements Runnable {
        @Override
        public void run() {
            startPlaySong();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 通知渠道的id
        String id = "my_channel_01";
        // 用户可以看到的通知渠道的名字.
        CharSequence name = "我找车";
//         用户可以看到的通知渠道的描述
        String description = "我找车运行中";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
//         配置通知渠道的属性
        mChannel.setDescription(description);
//         设置通知出现时的闪灯（如果 android 设备支持的话）
        mChannel.enableLights(false);
        mChannel.setLightColor(Color.RED);
//         设置通知出现时的震动（如果 android 设备支持的话）
        mChannel.enableVibration(false);
        //mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        mChannel.setVibrationPattern(new long[]{0});
        mNotificationManager.createNotificationChannel(mChannel);

        // 为该通知设置一个id
        int notifyID = 1;
        // 通知渠道的id
        String CHANNEL_ID = "my_channel_01";
        // Create a notification and set the notification channel.
        Notification notification = new Notification.Builder(this)
                .setContentTitle("我找车") .setContentText("正在运行中")
                .setSmallIcon(R.drawable.ico_app)
                .setChannelId(CHANNEL_ID)
                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
                .build();
        startForeground(1,notification);
    }
}

package com.saimawzc.freight.weight.utils.keeplive.onepixe;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ScreenService extends Service {
    public void onCreate() {
        super.onCreate();
        new ScreenListener(this).begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                // 亮屏，finish一个像素的Activity
                KeepLiveActivityManager.getInstance(ScreenService.this)
                        .finishKeepLiveActivity();
            }
            @Override
            public void onScreenOff() {
                // 灭屏，启动一个像素的Activity
                KeepLiveActivityManager.getInstance(ScreenService.this)
                        .startKeepLiveActivity();
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;

    }
}

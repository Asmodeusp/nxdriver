package com.saimawzc.freight.weight.utils.serves;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class RepetitionService extends IntentService {
    public static final String TAG = "msg";


    public RepetitionService() {
        super("RepetitionService");
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG, "onCreate");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        做一些逻辑，由于IntentService 会进行异步处理，
//        所以这里可以直接写耗时逻辑，不会占用主线程耗时，不需要再开启异步线程，
//        onHandleIntent 执行完后， Service会自动销毁；
        Log.e(TAG, "操作");
    }


    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

}

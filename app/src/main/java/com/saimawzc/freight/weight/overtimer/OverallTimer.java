package com.saimawzc.freight.weight.overtimer;

import android.util.Log;
import android.widget.Toast;

import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.constants.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

public class OverallTimer {

    public  static OverallTimer overAllTimer;
    public static Timer timer;

    public static OverallTimer  getInstance() {
        if (overAllTimer == null) {
            synchronized (OverallTimer.class){
                if(overAllTimer==null){
                    overAllTimer =  new OverallTimer();
                }
            }
        }
        return overAllTimer;
    }

    public static OverallTimer getOverAllTimer() {
        return overAllTimer;
    }

    public void startTimer(){
        if(overAllTimer==null){
            return;
        }
        if(timer==null){
            synchronized (OverallTimer.class){
                if(timer==null){
                    timer =  new Timer();
                }
            }
        }
        if (timer != null) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(BaseApplication.getInstance()==null){
                        return;
                    }
                    if(BaseApplication.getInstance().isGatherStarted&&BaseApplication.getInstance().isTraceStarted){
                        Log.e("msg","鹰眼正在运行中");
                    }else {
                        EventBus.getDefault().post(Constants.openTruck);
                    }
                }
            }, 10000, 5*60*1000);// 程序启动后,过10秒再执行,然后每隔5分钟执行一次。
        }
    }
    public void cancelTimer(){
        if (timer != null) {
            timer.cancel();
            timer=null;
        }
    }
}

package com.saimawzc.freight.weight.utils.keeplive.jobhander;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
/**
 * 定时器
 * 安卓5.0及以上
 */
@SuppressWarnings(value = {"unchecked", "deprecation"})
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobHandlerService extends JobService {
    /**
     * Job的时间
     */
    public static final int JOB_TIME = 1000*60;
    public static void startJob(Context context) {
        try {
            JobScheduler mJobScheduler = (JobScheduler) context.getSystemService(
                    Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(10,
                    new ComponentName(context.getPackageName(),
                            JobHandlerService.class.getName())).setPersisted(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //7.0以上延迟1s执行
                builder.setMinimumLatency(JOB_TIME);
            } else {
                //每隔1s执行一次job
                builder.setPeriodic(JOB_TIME);
            }
            mJobScheduler.schedule(builder.build());

        } catch (Exception e) {
            Log.e("startJob->", e.getMessage());
        }
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.e("msg","拉活APP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            startJob(this);
        }
        return false;
    }
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}

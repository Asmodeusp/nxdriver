package com.saimawzc.freight.weight.utils.app;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * Author : zhouyx
 * Date   : 2015/12/11
 * Activity管理类
 */
public class AppManager {

    Stack<WeakReference<Activity>> activityStack = new Stack<WeakReference<Activity>>();
    private static AppManager instance;

    private AppManager() {

    }

    public static AppManager get() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        WeakReference<Activity> mactivity = new WeakReference<Activity>(activity);
        activityStack.add(mactivity);
    }
    /**
     * 从堆栈中移除该Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }
    /**
     * 结束指定类名的Activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        for (WeakReference<Activity> activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                activityStack.remove(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (WeakReference<Activity> activity : activityStack) {
            if (activity != null) {
                if(activity.get()!=null){
                    activity.get().finish();
                }
            }
        }
        activityStack.clear();
    }



    /**
     * 检查栈里是否有目标Activity
     */
    public boolean containActivity(Class<?> cls) {
        for (WeakReference<Activity> activityWeakReference: activityStack) {
            if (activityWeakReference.get().getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取目标Activity
     */
    public Activity getActivity(Class<?> cls) {
        for (WeakReference<Activity> activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity.get();
            }
        }
        return null;
    }

}

package com.yiguohan.easylib;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * 全局相关工具类
 * Created by yiguohan on 2017/11/13.
 * github: https://www.github.com/yiguohan
 * email: yiguohan@gmail.com
 */

public final class GlobalUtils {

    private static Application sApplication;

    static WeakReference<Activity> sTopActivityWeakRef;
    /**
     * Activity管理集合
     * 使用LinkedList有利于在增删时的性能优化
     */
    static List<Activity> activities = new LinkedList<>();

    /**
     * Activity生命周期的回调
     */
    private static Application.ActivityLifecycleCallbacks mCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            activities.add(activity);
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            activities.remove(activity);
        }
    };

    /**
     * 把栈顶的Activity实例设置为弱引用(Weak Reference)有利于回收
     *
     * @param activity
     */
    private static void setTopActivityWeakRef(Activity activity) {
        if (sTopActivityWeakRef != null || !activity.equals(sTopActivityWeakRef.get())) {
            sTopActivityWeakRef = new WeakReference<Activity>(activity);
        }
    }

    /**
     * 初始化GlobalUtils
     * 把Application实例传入进行注册监听
     *
     * @param app 当前所在的App的Application实例
     */
    public static void init(final Application app) {
        GlobalUtils.sApplication = app;
        app.registerActivityLifecycleCallbacks(mCallbacks);
    }

    /**
     * 获取Application的实例
     * 获取前应先对GlobalUtils进行初始化
     *
     * @return
     */
    public static Application getApp() {
        if (sApplication != null) {
            return sApplication;
        }
        throw new NullPointerException("You should init Utils first!");
    }

}

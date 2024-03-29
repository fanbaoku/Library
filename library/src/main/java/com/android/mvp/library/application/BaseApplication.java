package com.android.mvp.library.application;

import android.app.Application;
import android.os.Handler;

//import com.github.anzewei.parallaxbacklayout.ParallaxHelper;

public class BaseApplication extends Application {

    // 获取到主线程的上下文
    private static BaseApplication mContext = null;
    // 获取到主线程的handler
    private static Handler mMainThreadHandler = null;
    // 获取到主线程
    private static Thread mMainThread = null;
    // 获取到主线程的id
    private static int mMainThreadId;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mMainThreadHandler = new Handler();
//                mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
//        registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
    }

    // 对外暴露上下文
    public static BaseApplication getApplication() {
        return mContext;
    }

    // 对外暴露主线程的handler
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    // 对外暴露主线程
   /* public static Thread getMainThread() {
        return mMainThread;
    }*/

    // 对外暴露主线程id
    public static int getMainThreadId() {
        return mMainThreadId;
    }
}

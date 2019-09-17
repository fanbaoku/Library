package com.android.mvp.library.utils;


/**
 * Created by zp on 7/26/16.
 */
public class FastClickUtil {
    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 300) {
            LogUtil.e("0.3秒间隔！！！");
            return true;
        }
        lastClickTime = time;
        return false;
    }


    private static long lastClickTime1;

    public synchronized static boolean isFastClick(long times) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime1 < times) {
//            LogUtils.e(times + "秒间隔！！！");
            return true;
        }
        lastClickTime1 = time;
        return false;
    }



    private static long lastGetTime;
    public synchronized static boolean isFastGet(long times) {
        long time = System.currentTimeMillis();
        if (time - lastGetTime < times) {
//            LogUtils.e(times + "秒间隔！！！");
            return true;
        }
        lastGetTime = time;
        return false;
    }


}

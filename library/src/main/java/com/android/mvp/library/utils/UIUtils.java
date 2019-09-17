package com.android.mvp.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.mvp.library.application.BaseApplication;


/**
 * <p>
 * Title:UIUtils
 * </p>
 * <p>
 * Description: 和UI相关的实用工具类
 * </p>
 *
 * @author Swh
 * @date 2015-10-9 下午3:50:19
 */
public class UIUtils {
    public static boolean isToast = false;


    /**
     * 获取上下文Context
     *
     * @return
     */
    public static Context getContext() {
        return BaseApplication.getApplication();
    }



    /**
     * 获取主线程
     *
     * @return
     */
//    public static Thread getMainThread() {
//        return BaseApplication.getMainThread();
//    }

    /**
     * 获取主线程ID
     *
     * @return
     */
    public static long getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }

    /**
     * dp转换成像素
     *
     * @param dp
     * @return
     */
    public static int dp2Px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //    /**
//     * dip转换px
//     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    //    /**
//     * pxz转换dip
//     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getHandler() {
        return BaseApplication.getMainThreadHandler();
    }

    /**
     * 延时在主线程执行runnable
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 在主线程执行runnable
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 从主线程looper里面移除runnable
     */
    /*public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }*/

    /**
     * 通过layout 文件生成View
     */
    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取整数
     */
    public static int getInt(int resId) {
        return getResources().getInteger(resId);
    }


    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     */
//    public static int getDimens(int resId) {
//        return getResources().getDimensionPixelSize(resId);
//    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }
    //返回绘画矩形图片的方法
    /*public static Drawable getGradientDrawable(int rgb,int r){
        GradientDrawable gradientDrawable = new GradientDrawable();
		gradientDrawable.setGradientType(GradientDrawable.RECTANGLE);
		gradientDrawable.setColor(rgb);
		gradientDrawable.setCornerRadius(r);
		return gradientDrawable;
	}*/

    /**
     * 生成背景选择器(1,选中背景2,未选中背景)的方法
     *
     * @param drawablePress
     * @param drawableNormal
     * @return
     */
    public static StateListDrawable getStateListDrawable(
            Drawable drawablePress, Drawable drawableNormal) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        // 添加处在当前状态下的图片
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled,
                android.R.attr.state_pressed}, drawablePress);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled},
                drawableNormal);
        stateListDrawable.addState(new int[]{}, drawableNormal);
        return stateListDrawable;
    }

    // 判断当前的线程是不是在主线程
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    /**
     * 获得状态栏的高度
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = DataUtil.getIntByString(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 状态栏高度算法
     *
     * @param activity
     * @return
     */
  /*  public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = DataUtil.getIntByString(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }*/

    /**
     * 重计算ListView高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null)
            return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 获取屏幕尺寸
     *
     * @param activity Activity
     * @return 屏幕尺寸像素值，下标为0的值为宽，下标为1的值为高
     */
    public static int[] getScreenSize(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return new int[]{metrics.widthPixels, metrics.heightPixels};
    }

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenWidth() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static int getScreenHeight() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    public static int[] getViewSize(final View view) {
        final int[] ints = new int[2];
        ViewTreeObserver vto2 = view.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ints[0] = view.getWidth();
                ints[1] = view.getHeight();
            }
        });
        return ints;
    }

    /**
     * @param toBeDelegated    需要被扩大点击的view
     * @param expandRadiusInPx 扩大的半径(方形区域)
     * @description: 扩大view的点击范围 最大只能扩大到parent这么大
     * @autour: zhengpeng
     * @date: 17/7/4 上午10:32
     */

    public static void setTouchDelegate(final View toBeDelegated, final int expandRadiusInPx) {
        toBeDelegated.post(new Runnable() {
            @Override
            public void run() {
                Rect rc = new Rect();
                toBeDelegated.getHitRect(rc); //如果直接在oncreate函数中执行本函数，会获取rect失败，因为此时UI界面尚未开始绘制，无法获得正确的坐标
                rc.bottom += expandRadiusInPx;
                rc.top -= expandRadiusInPx;
                rc.left -= expandRadiusInPx;
                rc.right += expandRadiusInPx;

                ((View) toBeDelegated.getParent()).setTouchDelegate(new TouchDelegate(rc, toBeDelegated));

            }
        });
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                            int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 100, 100);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }
}

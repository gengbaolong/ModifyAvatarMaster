package com.seven.modifyavatarmaster.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by ChenJunMei on 2016/11/22.
 * * 作用：单位转换工具
 * px和dp互相转换工具
 */

public class DensityUtil {

    public static final int STANDARD_SCREEN_WIDTH = 720;
    public static final int STANDARD_SCREEN_HEIGHT = 1280;
    public static final int STANDARD_SCREEN_DENSITY = 320;

    private static WindowManager windowManager;

    private static WindowManager getWindowManager(Context context) {
        if (windowManager == null) {
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return windowManager;
    }
    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    // 屏幕高度（像素）
    public static int getWindowHeight(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        try {
            Display defaultDisplay = getWindowManager(context).getDefaultDisplay();

            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getMetrics(displayMetrics);
            if (displayMetrics.widthPixels > 0) {
                return displayMetrics.widthPixels;
            }

            Point screenSize = new Point();
            defaultDisplay.getSize(screenSize);
            if (screenSize.x > 0) {
                return screenSize.x;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return STANDARD_SCREEN_WIDTH;
    }

    public static int getScreenHeight(Context context) {
        try {
            Display defaultDisplay = getWindowManager(context).getDefaultDisplay();

            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getMetrics(displayMetrics);
            if (displayMetrics.heightPixels > 0) {
                return displayMetrics.heightPixels;
            }

            Point screenSize = new Point();
            defaultDisplay.getSize(screenSize);
            if (screenSize.y > 0) {
                return screenSize.y;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return STANDARD_SCREEN_HEIGHT;
    }
}


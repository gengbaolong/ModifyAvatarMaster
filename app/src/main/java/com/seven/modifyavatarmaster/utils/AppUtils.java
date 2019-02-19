package com.seven.modifyavatarmaster.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

/**
 * Created : 2018/10/18 20:01
 * Description :
 * Author : gengbaolong
 */

public class AppUtils {

    /**
     * 检测是否安装有支付宝
     *
     * @return
     */
    public static boolean checkAliPayIsInstalledOrNot(Context context) {
        String url = "alipays://platformapi/startApp";
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean checkWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断 用户是否安装QQ客户端
     */
    public static boolean checkQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 对比两个版本号判断是否需要更新
     *
     * @param serverVersion
     * @return
     */
    public static boolean checkNeedUpdate(String appVerison, String serverVersion) {
        boolean needUpdate = false;
        try {
            String[] appArray = appVerison.replace(" ", "").split("\\.");
            String[] serverArray = serverVersion.replace(" ", "").split("\\.");
            if (appArray != null && serverArray != null && appArray.length == 3 && serverArray.length == 3) {
                if (StringUtil.compareString(appArray[0], serverArray[0])) {
                    needUpdate = true;
                } else {
                    if (appArray[0].equals(serverArray[0])) {
                        if (StringUtil.compareString(appArray[1], serverArray[1])) {
                            needUpdate = true;
                        } else {
                            if (appArray[1].equals(serverArray[1])) {
                                if (StringUtil.compareString(appArray[2], serverArray[2])) {
                                    needUpdate = true;
                                }
                            }
                        }
                    }
                }
//                if(compareString(appArray[0],serverArray[0])||compareString(appArray[1],serverArray[1])
//                        ||compareString(appArray[2],serverArray[2])){
//                    needUpdate = true;
//                }
            }
        } catch (Exception e) {
            needUpdate = false;
        }
        return needUpdate;
    }

    /**
     * 随机手机号码
     *
     * @return
     */
    public static String randomPhone() {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4) {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer phoneNum = new StringBuffer();
        for (Integer i : set) {
            phoneNum.append("" + i);
        }
        return phoneNum.toString();
    }

    /**
     * 获取两个数据之间的整数(含头不含尾)
     *
     * @param smallData
     * @param bigData
     * @return
     */
    public static int randomIntFromTwo(int smallData, int bigData) {
        Random rand = new Random();
        int num = 0;
        if (bigData != smallData) {
            while (true) {
                int i = rand.nextInt(bigData+1 - smallData);
                if (i % 100 == 0) {
                    num = smallData + i;
                    break;
                }
            }
        }
        return num;
    }


    // 检测MIUI
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUI() {
//        if(SPUtils.getInstance().getCacheDataSP().contains("isMIUI"))
//        {
//            return SPUtils.getInstance().getCacheDataSP().getBoolean("isMIUI",false);
//        }
        Properties prop = new Properties();
        boolean isMIUI;
        try {
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        isMIUI = prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
//        SPUtils.getInstance().putCacheData("isMIUI",isMIUI);//保存是否MIUI
        return isMIUI;
    }


    public static boolean existSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }


}

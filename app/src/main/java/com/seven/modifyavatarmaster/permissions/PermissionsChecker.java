package com.seven.modifyavatarmaster.permissions;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.support.v4.content.ContextCompat;

/**
 * Created : 2018/10/23 16:37
 * Description :
 * Author : gengbaolong
 */

public class PermissionsChecker {
    private final Context mContext;

    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {

        for (String permission : permissions) {
            if(!checkOpsPermission(permission)){
                return true;
            }
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }
    public  boolean checkOpsPermission(String permission) {
        if(Build.VERSION.SDK_INT>=23) {
            try {
                AppOpsManager appOpsManager = (AppOpsManager) mContext.getSystemService(Context.APP_OPS_SERVICE);
                String opsName = AppOpsManager.permissionToOp(permission);
                if (opsName == null) {
                    return true;
                }
                int opsMode = appOpsManager.checkOpNoThrow(opsName, Process.myUid(), mContext.getPackageName());
                return opsMode == AppOpsManager.MODE_ALLOWED;
            } catch (Exception ex) {
                return true;
            }
        }
        return true;
    }
    private  boolean hasPermissionIgnored(String permission) {
        if(Build.VERSION.SDK_INT>=23) {
            try {
                AppOpsManager appOpsManager = (AppOpsManager) mContext.getSystemService(Context.APP_OPS_SERVICE);
                String opsName = AppOpsManager.permissionToOp(permission);
                if (opsName == null) {
                    return false;
                }
                int opsMode = appOpsManager.checkOpNoThrow(opsName, Process.myUid(), mContext.getPackageName());
                return opsMode == AppOpsManager.MODE_IGNORED;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    /**
     * 判断权限是否被拒绝 miui
     * @param permissions
     * @return
     */
    public boolean hasPermissionIgnored(String... permissions) {
        for (String permission : permissions) {
            if(hasPermissionIgnored(permission)){
                return true;
            }
        }
        return false;
    }
    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
}

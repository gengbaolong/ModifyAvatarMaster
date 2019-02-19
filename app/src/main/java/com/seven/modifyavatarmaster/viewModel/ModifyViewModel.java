package com.seven.modifyavatarmaster.viewModel;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.seven.modifyavatarmaster.databinding.ActivityModifyAvatarBinding;
import com.seven.modifyavatarmaster.permissions.PermissionsActivity;
import com.seven.modifyavatarmaster.utils.DialogUtils;
import com.seven.modifyavatarmaster.view.CameraDialogChooseInterface;

/**
 * Created : 2018/10/26 14:37
 * Description :
 * Author : gengbaolong
 */

public class ModifyViewModel extends ViewModel implements CameraDialogChooseInterface {

    private Context mContext;
    private ActivityModifyAvatarBinding modifyAvatarBinding;
    private CameraDialogChooseInterface mChooseInterface;

    //系统相机
    public static final int REQUEST_CODE_CAMERA_PERMISSION = 101;
    //系统相册
    public static final int REQUEST_CODE_PHOTO_PERMISSION = 102;
    //所需要的权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //拍照所需要的权限
    static final String[] PERMISSIONS_CAMERA = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public ModifyViewModel(Context context, ActivityModifyAvatarBinding binding){
        this.mContext = context;
        this.modifyAvatarBinding = binding;
        mChooseInterface = ModifyViewModel.this;
        initView();
    }

    private void initView() {
        modifyAvatarBinding.ivRightBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showCameraDialog(mContext, mChooseInterface);
            }
        });
    }

    @Override
    public void onCameraChoose(int cameraType) {
        switch (cameraType){
            case 0://拍照
                PermissionsActivity.startActivityForResult((Activity) mContext, REQUEST_CODE_CAMERA_PERMISSION, PERMISSIONS_CAMERA);//打开系统相机需要相机权限
                break;
            case 1://系统相册
                //跳转到系统相册
                PermissionsActivity.startActivityForResult((Activity) mContext, REQUEST_CODE_PHOTO_PERMISSION, PERMISSIONS);
                break;
        }
    }


    @Override
    public void onDestroy() {

    }

    @Override
    public void onResume() {

    }
}

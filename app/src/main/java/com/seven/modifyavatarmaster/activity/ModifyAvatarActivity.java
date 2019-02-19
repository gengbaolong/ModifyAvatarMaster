package com.seven.modifyavatarmaster.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.seven.modifyavatarmaster.R;
import com.seven.modifyavatarmaster.databinding.ActivityModifyAvatarBinding;
import com.seven.modifyavatarmaster.permissions.PermissionsActivity;
import com.seven.modifyavatarmaster.utils.AppUtils;
import com.seven.modifyavatarmaster.utils.FileUtils;
import com.seven.modifyavatarmaster.utils.PictureUtil;
import com.seven.modifyavatarmaster.viewModel.ModifyViewModel;

import java.io.File;

public class ModifyAvatarActivity extends AppCompatActivity {

    private ModifyViewModel viewModel;
    private ActivityModifyAvatarBinding modifyAvatarBinding;

    private final int PHOTO_PICKED_FROM_CAMERA = 1; // 用来标识头像来自系统拍照
    private final int PHOTO_PICKED_FROM_FILE = 2; // 用来标识从相册获取头像
    private final int CROP_FROM_CAMERA = 3;
    private static String IMAGE_FILE_NAME = "user_head_icon.jpg";

    private Intent mIntent = null;
    private int mResultCode;
    private String mUri;
    private File mCropImageFile;


    public static void startActivityForResult(Context context, Uri imgUri, int requestCode, int resultCode) {
        Intent intent = new Intent(context, ModifyAvatarActivity.class);
        intent.putExtra("RESULTCODE", resultCode);
        if (null != imgUri) {
            intent.putExtra("IMAGEURI", imgUri.toString());
        }
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding(DataBindingUtil.setContentView(this,getLayoutId()));
        viewModel = getViewModel();
        bindViewModel(modifyAvatarBinding,viewModel);
        init();
    }

    private void setBinding(ViewDataBinding viewDataBinding) {
        this.modifyAvatarBinding = (ActivityModifyAvatarBinding) viewDataBinding;
    }

    private void init() {
        IMAGE_FILE_NAME = System.currentTimeMillis() + IMAGE_FILE_NAME;//根据时间创建路径
        mResultCode = getIntent().getIntExtra("RESULTCODE", 0);
        mUri = getIntent().getStringExtra("IMAGEURI");
        try {
            if (!TextUtils.isEmpty(mUri)) {
                Uri imgUri = Uri.parse(mUri);
                if (imgUri != null) {
                    //显示大图
                    modifyAvatarBinding.photoView.setImageURI(imgUri);//设置图片
                }
            }else{
                modifyAvatarBinding.photoView.setImageResource(R.mipmap.avatar_icon);
            }
        } catch (Exception e) {

        }

        modifyAvatarBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIntent != null) {
                    setResult(mResultCode, mIntent);
                }
                finish();
            }
        });
    }

    private ModifyViewModel getViewModel() {
        return new ModifyViewModel(this, modifyAvatarBinding);
    }

    private int getLayoutId() {
        return R.layout.activity_modify_avatar;
    }

    private void bindViewModel(ActivityModifyAvatarBinding binding, ModifyViewModel viewModel) {
        binding.setViewModel(viewModel);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //系统相机权限
        if (requestCode == ModifyViewModel.REQUEST_CODE_CAMERA_PERMISSION) {
            if (resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            } else {
                imageCapture();//系统相机拍照
            }
        }

        //拍照完成的回调
        if (requestCode == PHOTO_PICKED_FROM_CAMERA && resultCode == Activity.RESULT_OK) {//Activity.RESULT_OK可以确保拍照后有回调结果，屏蔽了返回键的回调
            startSystemCamera();
        }

        //裁剪的图片的回调
        if (requestCode == CROP_FROM_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                Uri cropUri = Uri.fromFile(mCropImageFile);
                setPicToView();
            }

        }

        //系统相册
        if (requestCode == ModifyViewModel.REQUEST_CODE_PHOTO_PERMISSION) {
            if (resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            } else {
                chooseImageSys();//打开系统相册
            }
        }
        //从相册选择图片之后
        if (requestCode == PHOTO_PICKED_FROM_FILE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = PictureUtil.getImageUri(this, data);
                startPhotoZoom(uri);
            }
        }
    }


    //调用系统相册
    private void chooseImageSys() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PHOTO_PICKED_FROM_FILE);
    }

    /**
     * 系统拍照后裁剪
     */
    public void startSystemCamera() {
        File pictureFile = new File(PictureUtil.getMyPetRootDirectory(), IMAGE_FILE_NAME);
        Uri pictureUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pictureUri = FileProvider.getUriForFile(this,
                    "com.seven.modifyavatarmaster.fileprovider", pictureFile);
        } else {
            pictureUri = Uri.fromFile(pictureFile);
        }
        startPhotoZoom(pictureUri);
    }

    public void startPhotoZoom(Uri uri) {
        try {
            if (AppUtils.existSDCard()) {
                mCropImageFile = FileUtils.createTmpFile(this);
                Intent intent = new Intent("com.android.camera.action.CROP");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                intent.setDataAndType(uri, "image/*");
                intent.putExtra("crop", "true");
                if (Build.MANUFACTURER.equals("HUAWEI")) {//解决华为手机调用裁剪出现圆形裁剪框
                    intent.putExtra("aspectX", 9998);
                    intent.putExtra("aspectY", 9999);
                } else {
                    intent.putExtra("aspectX", 1); // 裁剪框比例
                    intent.putExtra("aspectY", 1);
                }
                intent.putExtra("outputX", 300); // 输出图片大小
                intent.putExtra("outputY", 300);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", false);
                intent.putExtra("circleCrop", false);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCropImageFile));
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                intent.putExtra("noFaceDetection", true); // no face detection
                startActivityForResult(intent, CROP_FROM_CAMERA);
            }
        } catch (Exception e) {

        }

    }


    private void imageCapture() {
        Intent intent;
        Uri pictureUri;
        //getMyPetRootDirectory()得到的是Environment.getExternalStorageDirectory() + File.separator+"."
        //也就是我之前创建的存放头像的文件夹（目录）
        File pictureFile = new File(PictureUtil.getMyPetRootDirectory(), IMAGE_FILE_NAME);
        // 判断当前系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //这一句非常重要
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //""中的内容是随意的，但最好用package名.provider名的形式，清晰明了
            pictureUri = FileProvider.getUriForFile(this,
                    "com.seven.modifyavatarmaster.fileprovider", pictureFile);
        } else {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureUri = Uri.fromFile(pictureFile);
        }
        // 去拍照,拍照的结果存到pictureUri对应的路径中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(intent, PHOTO_PICKED_FROM_CAMERA);
    }

    public void setPicToView() {
        Intent intent = new Intent();
        if (mCropImageFile != null) {
            intent.putExtra("IMAGE_PATH", mCropImageFile.getAbsolutePath());
            setResult(mResultCode, intent);
            finish();
        }
    }

}

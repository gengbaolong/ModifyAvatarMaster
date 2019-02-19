package com.seven.modifyavatarmaster.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.seven.modifyavatarmaster.R;
import com.seven.modifyavatarmaster.utils.GlideLoadUtils;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_avatar;
    private Uri imgUri = null;
    private String imagePath;
    private File imageFile;

    private final int PHOTO_PICKED_REQUEST_CODE = 201; // 用来标识跳转到头像修改
    private final int PHOTO_PICKED_RESULT_CODE = 202; // 用来标识头像修改跳转回来

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        iv_avatar = findViewById(R.id.iv_avatar);
        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyAvatarActivity.startActivityForResult(MainActivity.this, imgUri, PHOTO_PICKED_REQUEST_CODE, PHOTO_PICKED_RESULT_CODE);//跳转到修改页面,回来的时候将图片带过来
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_PICKED_REQUEST_CODE && resultCode == PHOTO_PICKED_RESULT_CODE) {
            if (data != null) {
                setCropImg(data);
            }
        }
    }

    private void setCropImg(Intent picData) {
        //回调成功
        imagePath = picData.getStringExtra("IMAGE_PATH");
        imageFile = new File(imagePath);
        imgUri = Uri.fromFile(imageFile);
        GlideLoadUtils.getInstance().glideAvatarLoad(this, imagePath, iv_avatar, R.mipmap.avatar_icon);
    }
}

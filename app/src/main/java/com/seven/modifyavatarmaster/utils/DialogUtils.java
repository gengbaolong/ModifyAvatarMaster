package com.seven.modifyavatarmaster.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.seven.modifyavatarmaster.R;
import com.seven.modifyavatarmaster.view.CameraDialogChooseInterface;
import com.seven.modifyavatarmaster.widget.CustomDialog;


/**
 * Created by ps on 2017/8/16.
 */

public class DialogUtils {

    public static void showStandardDialog(Context context, String content,  String leftButtonText, String rightButtonText, DialogInterface.OnClickListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setLeftButtonText(leftButtonText)
                .setRightButtonText(rightButtonText)
                .setCanceledOnTouchOutside(false)
                .setContent(content)
                .setButtonListener(listener);
        builder.create().show();
    }
    public static void showStandardDialog(Context context, String content,int contentSize,  String leftButtonText, String rightButtonText, DialogInterface.OnClickListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setLeftButtonText(leftButtonText)
                .setRightButtonText(rightButtonText)
                .setCanceledOnTouchOutside(false)
                .setContent(content)
                .setContentSize(contentSize)
                .setButtonListener(listener);
        builder.create().show();
    }
    public static void showStandardDialog(Context context, String content,   String rightButtonText, DialogInterface.OnClickListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder
                .setRightButtonText(rightButtonText)
                .setCanceledOnTouchOutside(false)
                .setContent(content)
                .setButtonListener(listener);
        builder.create().show();
    }

    public static void showAccurateStandardDialog(Context context, String content,  String leftButtonText, String rightButtonText, DialogInterface.OnClickListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setLeftButtonText(leftButtonText)
                .setRightButtonText(rightButtonText)
                .setCanceledOnTouchOutside(false)
                .setContent(content)
                .setContentSize(15)
                .setButtonListener(listener);
        builder.create().show();
    }

    /**
     * 显示dialog屏蔽系统返回键
     * @param context
     * @param content
     * @param rightButtonText
     * @param listener
     */
    public static void showStandardDialogMaskSystemBack(Context context, String content,   String rightButtonText, DialogInterface.OnClickListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder
                .setRightButtonText(rightButtonText)
                .setCanceledOnTouchOutside(false)
                .setContent(content)
                .setButtonListener(listener);
        CustomDialog dialog = builder.create();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
        dialog.show();
    }
    /**
     * 显示选择相机dialog
     * @param context
     * @param chooseInterface
     */
    public static void  showCameraDialog(Context context,  final CameraDialogChooseInterface chooseInterface){
        final CustomDialog dialog;
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_camera_choose, null);
        TextView camera_type = (TextView) view.findViewById(R.id.camera_type);
        TextView tv_system_camera = (TextView) view.findViewById(R.id.tv_system_camera);
        TextView camera_cancle = (TextView) view.findViewById(R.id.camera_cancle);

        builder.setContentView(view)
                .setCanceledOnTouchOutside(true)
                .setGravity(Gravity.BOTTOM)
                .setMargin(0);
        dialog = builder.create();
        camera_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseInterface.onCameraChoose(0);
                if(dialog !=null){
                    dialog.dismiss();
                }
            }
        });
        tv_system_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseInterface.onCameraChoose(1);
                if(dialog !=null){
                    dialog.dismiss();
                }
            }
        });
        camera_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog !=null){
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

}

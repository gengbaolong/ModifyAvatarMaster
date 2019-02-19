package com.seven.modifyavatarmaster.widget;

/**
 * Created by ps on 2017/8/16.
 */


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.seven.modifyavatarmaster.R;

import java.util.ArrayList;

/**
 * Created by Majiaxing on 2016/10/25.
 */

public class CustomDialog extends Dialog {
    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder implements View.OnClickListener {

        private Context context;
        private CustomDialog dialog;
        private int margin = 20;//对话框离屏幕左右边界的距离
        private boolean displayContentView = true;//是否显示中间内容
        private boolean displayBottomView = true;//是否显示底部按钮

        //内容View
        private RelativeLayout rlContentView;
        private TextView contentTv, contentTv2, subContentTv;
        private String contentText, contentText2, subContentText;
        private int contentColor = Color.BLACK, subContentColor = Color.BLACK, contentSize = 20, subContentSize = 15;
        //自定义外部view
        private View externalView;

        //按钮View
        private RelativeLayout btnRLView;
        private View dialogView, btnSeparater;
        private Button leftBtn, rightBtn;
        private String leftBtnText, rightBtnText;
        private int leftColor = Color.parseColor("#007aff"), rightColor = Color.parseColor("#007aff");

        private OnClickListener btnClickListener;
        private boolean canceledOnOutside;
        private boolean isCover = true;//是否需要蒙板

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置点击对话框外的部分关闭
         *
         * @param canTouch true 关闭 ,false 不关闭,默认为false
         * @return
         */
        public Builder setCanceledOnTouchOutside(boolean canTouch) {
            this.canceledOnOutside = canTouch;
            return this;
        }


        /**
         * 显示内容部分view
         *
         * @param displayContentView
         * @return
         */
        public Builder setDisplayContentView(boolean displayContentView) {
            this.displayContentView = displayContentView;
            return this;
        }

        /**
         * 显示底部按钮
         *
         * @param displayBottomView
         * @return
         */
        public Builder setDisplayBottomView(boolean displayBottomView) {
            this.displayBottomView = displayBottomView;
            return this;
        }

        /**
         * 是否需要蒙版
         *
         * @param cover
         * @return
         */
        public Builder setCover(boolean cover) {
            this.isCover = cover;
            return this;
        }


        /**
         * 设置内容文本,为空或不设置则不显示
         *
         * @param content    设置一级内容
         * @param subContent 设置子标题内容
         * @return
         */
        public Builder setContent(String content, String subContent) {
            this.contentText = content;
            this.subContentText = subContent;
            return this;
        }

        /**
         * 设置内容文本,为空或不设置则不显示
         *
         * @param content      设置一级内容
         * @param contentText2 设置一级2内容
         * @return
         */
        public Builder setContent2(String content, String contentText2) {
            this.contentText = content;
            this.contentText2 = contentText2;
            return this;
        }


        /**
         * 设置内容文本
         *
         * @param content
         * @return
         */
        public Builder setContent(String content) {
            this.contentText = content;
            return this;
        }

        public Builder setContent(int textcolor, int textsize) {
            setContentColor(textcolor);
            setContentSize(textsize);
            return this;
        }

        public Builder setContentColor(int textcolor) {
            this.contentColor = textcolor;
            return this;
        }

        public Builder setContentSize(int textsize) {
            if (textsize > 0)
                this.contentSize = textsize;
            return this;
        }

        /**
         * 设置二级内容文本,为空或不设置则不显示
         *
         * @param content
         * @return
         */
        public Builder setSubContent(String content) {
            this.subContentText = content;
            return this;
        }

        public Builder setSubContent(int textcolor, int textsize) {
            setSubContentColor(textcolor);
            setSubContentSize(textsize);
            return this;
        }

        public Builder setSubContentColor(int textcolor) {
            this.subContentColor = textcolor;
            return this;
        }

        public Builder setSubContentSize(int textsize) {
            if (textsize > 0)
                this.subContentSize = textsize;
            return this;
        }

        /**
         * 设置外部的内容view替换默认的内容view
         *
         * @param contentView
         * @return
         */
        public Builder setContentView(View contentView) {
            this.externalView = contentView;
            return this;
        }

        public int px2dip(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }

        /**
         * dip转px
         *
         * @param context
         * @param dipValue
         * @return
         */
        public int dip2px(Context context, float dipValue) {
            try {
                final float scale = context.getResources().getDisplayMetrics().density;
                return (int) (dipValue * scale + 0.5f);
            } catch (Exception e) {
                // TODO: handle exception
                return (int) dipValue;
            }
        }

        public int getScreenWidth() {
            return ((WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getWidth();
        }

        /**
         * 设置left按钮监听事件
         *
         * @param listener
         * @return
         */
        public Builder setButtonListener(OnClickListener listener) {
            this.btnClickListener = listener;
            return this;
        }


        /**
         * 设置左边按钮文字和颜色，文字为空或不设置则不显示
         *
         * @param text
         * @param color
         * @return
         */
        public Builder setLeftButtonText(String text, int color) {
            this.leftBtnText = text;
            this.leftColor = color;
            return this;
        }

        /**
         * 设置左边按钮文字和颜色，文字为空或不设置则不显示
         *
         * @param text
         * @return
         */
        public Builder setLeftButtonText(String text) {
            this.leftBtnText = text;
            return this;
        }

        /**
         * 设置右边按钮文字和颜色，文字为空或不设置则不显示
         *
         * @param text
         * @param color
         * @return
         */
        public Builder setRightButtonText(String text, int color) {
            this.rightBtnText = text;
            this.rightColor = color;
            return this;
        }

        /**
         * 设置右边按钮文字，文字为空或不设置则不显示
         *
         * @param text
         * @return
         */
        public Builder setRightButtonText(String text) {
            this.rightBtnText = text;
            return this;
        }


        /**
         * 设置对话框离屏幕左右边界的距离
         *
         * @param margin
         * @return
         */
        public Builder setMargin(int margin) {
            this.margin = margin;
            return this;
        }

        /**
         * 设置显示位置集合，可设置多种组合显示方式
         *
         * @param gravties
         * @return
         */
        public Builder setGravity(ArrayList<Integer> gravties) {
            this.gravitys = gravties;
            return this;
        }

        /**
         * 设置显示位置
         *
         * @param gravity
         * @return
         */
        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        private ArrayList<Integer> gravitys = new ArrayList<Integer>();//对齐方式集合
        private int gravity = Gravity.CENTER;//对齐方式
        private boolean isFullScreen = true;//自定义view宽度是否充满全屏

        public Builder setFullScreen(boolean fullScreen) {
            isFullScreen = fullScreen;
            return this;
        }

        @SuppressLint("InflateParams")
        public CustomDialog create() {
            if (isCover) {
                dialog = new CustomDialog(context, R.style.CustomDialog);
            } else {
                dialog = new CustomDialog(context, R.style.CustomTipsDialog);
            }

            LayoutInflater mLayoutInflater = LayoutInflater.from(context);
            dialogView = mLayoutInflater.inflate(R.layout.layout_custom_dialog, null);

            dialog.addContentView(dialogView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));


            //设置对话框距屏幕的左右边距
//            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) dialogView.getLayoutParams();
//            params.width = getScreenWidth() - dip2px(context, margin) * 2;
//            dialogView.setLayoutParams(params);

            Window dialogWindow = dialog.getWindow();
            dialogWindow.setWindowAnimations(R.style.DialogAnimation);
            if (gravitys != null && gravitys.size() > 0) {
                for (int gravity : gravitys)
                    this.gravity |= gravity;
                dialogWindow.setGravity(gravity);
            } else {
                dialogWindow.setGravity(gravity);
            }


            initView();
            initData();
            setListener();
            if (externalView != null) {
                dialogView.setBackgroundColor(Color.TRANSPARENT);
                if (isFullScreen) {
                    //设置dialog边界距离
                    WindowManager m = dialogWindow.getWindowManager();
                    Display d = m.getDefaultDisplay();
                    WindowManager.LayoutParams p = dialogWindow.getAttributes();
                    p.width = (int) d.getWidth() - dip2px(context, margin);
                    dialogWindow.setAttributes(p);
                }
            } else {
                //设置dialog边界距离
                WindowManager m = dialogWindow.getWindowManager();
                Display d = m.getDefaultDisplay();
                WindowManager.LayoutParams p = dialogWindow.getAttributes();
                p.width = dip2px(context, 270);
                dialogWindow.setAttributes(p);
            }
            return dialog;
        }

        /**
         * 设置自定义动画
         *
         * @param style 0：不带动画   1：自定义动画
         */
        @SuppressLint("InflateParams")
        public CustomDialog create2(int style) {
            if (isCover) {
                dialog = new CustomDialog(context, R.style.CustomDialog);
            } else {
                dialog = new CustomDialog(context, R.style.CustomTipsDialog);
            }
            LayoutInflater mLayoutInflater = LayoutInflater.from(context);
            dialogView = mLayoutInflater.inflate(R.layout.layout_custom_dialog, null);
            dialog.addContentView(dialogView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Window dialogWindow = dialog.getWindow();
            if (style == 0) {//不需要动画
            } else {//自定义动画
                dialogWindow.setWindowAnimations(style);
            }
            if (gravitys != null && gravitys.size() > 0) {
                for (int gravity : gravitys)
                    this.gravity |= gravity;
                dialogWindow.setGravity(gravity);
            } else {
                dialogWindow.setGravity(gravity);
            }
            initView();
            initData();
            setListener();
            if (externalView != null) {
                dialogView.setBackgroundColor(Color.TRANSPARENT);
                if (isFullScreen) {
                    //设置dialog边界距离
                    WindowManager m = dialogWindow.getWindowManager();
                    Display d = m.getDefaultDisplay();
                    WindowManager.LayoutParams p = dialogWindow.getAttributes();
                    p.width = (int) d.getWidth() - dip2px(context, margin);
                    dialogWindow.setAttributes(p);
                }
            } else {
                //设置dialog边界距离
                WindowManager m = dialogWindow.getWindowManager();
                Display d = m.getDefaultDisplay();
                WindowManager.LayoutParams p = dialogWindow.getAttributes();
                p.width = dip2px(context, 270);
                dialogWindow.setAttributes(p);
            }
            return dialog;
        }

        private void initView() {
            // TODO Auto-generated method stub

            //内容View
            rlContentView = (RelativeLayout) dialogView.findViewById(R.id.custom_dialog_rl_contentView);

            contentTv = (TextView) dialogView.findViewById(R.id.custom_dialog_tv_content);
            contentTv2 = (TextView) dialogView.findViewById(R.id.custom_dialog_tv_content2);
            subContentTv = (TextView) dialogView.findViewById(R.id.custom_dialog_tv_subcontent);
            //底部按钮view
            btnRLView = (RelativeLayout) dialogView.findViewById(R.id.custom_dialog_rl_btnview);
            leftBtn = (Button) dialogView.findViewById(R.id.custom_dialog_leftbtn);
            rightBtn = (Button) dialogView.findViewById(R.id.custom_dialog_rightbtn);
            btnSeparater = dialogView.findViewById(R.id.custom_dialog_btn_separate);
        }

        private void initData() {
            // TODO Auto-generated method stub

            initContentViewData();
            initBottomViewData();
        }

        //设置监听
        public void setListener() {
            dialog.setCanceledOnTouchOutside(canceledOnOutside);
            if (null != btnClickListener) {
                leftBtn.setOnClickListener(this);
                rightBtn.setOnClickListener(this);
            }

        }

        private void initContentViewData() {
            if (externalView == null) {
                if (displayContentView && !(TextUtils.isEmpty(contentText) && TextUtils.isEmpty(subContentText))) {
                    if (rlContentView.getVisibility() != View.VISIBLE) {
                        rlContentView.setVisibility(View.VISIBLE);
                    }
                    //设置一级文本内容
                    if (!TextUtils.isEmpty(contentText)) {
                        if (contentTv.getVisibility() != View.VISIBLE) {
                            contentTv.setVisibility(View.VISIBLE);
                        }
                        contentTv.setText(contentText);
                        contentTv.setTextColor(contentColor);
                        if (contentSize > 0)
                            contentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, contentSize);
                    } else {
                        contentTv.setVisibility(View.GONE);
                    }

                    //设置一级文本2内容
                    if (!TextUtils.isEmpty(contentText2)) {
                        if (contentTv2.getVisibility() != View.VISIBLE) {
                            contentTv2.setVisibility(View.VISIBLE);
                        }
                        contentTv2.setText(contentText2);
                        contentTv2.setTextColor(contentColor);
                        if (contentSize > 0)
                            contentTv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, contentSize);
                    } else {
                        contentTv2.setVisibility(View.GONE);
                    }

                    //设置二级文本内容
                    if (!TextUtils.isEmpty(subContentText)) {
                        if (subContentTv.getVisibility() != View.VISIBLE) {
                            subContentTv.setVisibility(View.VISIBLE);
                        }
                        subContentTv.setText(subContentText);
                        subContentTv.setTextColor(subContentColor);
                        if (subContentSize > 0)
                            subContentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, subContentSize);
                    } else {
                        subContentTv.setVisibility(View.GONE);
                    }
                } else {
                    rlContentView.setVisibility(View.GONE);
                }
            } else {
                //设置外部内容view
                if (rlContentView.getVisibility() != View.VISIBLE) {
                    rlContentView.setVisibility(View.VISIBLE);
                }
                rlContentView.removeAllViews();
                rlContentView.addView(externalView);

//                LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) rlContentView.getLayoutParams();
//                lp.width=dip2px(context,355);
//                rlContentView.setLayoutParams(lp);
                rlContentView.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        private void initBottomViewData() {
            if (displayBottomView && !(TextUtils.isEmpty(leftBtnText) && TextUtils.isEmpty(rightBtnText))) {
                if (btnRLView.getVisibility() != View.VISIBLE) {
                    btnRLView.setVisibility(View.VISIBLE);
                }
                //设置left按钮文字和颜色
                if (!TextUtils.isEmpty(leftBtnText)) {
                    if (leftBtn.getVisibility() != View.VISIBLE) {
                        leftBtn.setVisibility(View.VISIBLE);
                        btnSeparater.setVisibility(View.VISIBLE);
                    }
                    leftBtn.setBackgroundResource(R.drawable.btn_left_selector);
                    leftBtn.setText(leftBtnText);
                    leftBtn.setTextColor(leftColor);
                } else {
                    leftBtn.setVisibility(View.GONE);
                    btnSeparater.setVisibility(View.GONE);
                }

                //设置right按钮文字和颜色
                if (!TextUtils.isEmpty(rightBtnText)) {
                    if (rightBtn.getVisibility() != View.VISIBLE)
                        rightBtn.setVisibility(View.VISIBLE);
                    rightBtn.setBackgroundResource(R.drawable.btn_right_selector);
                    rightBtn.setText(rightBtnText);
                    rightBtn.setTextColor(rightColor);
                } else {
                    rightBtn.setVisibility(View.GONE);
                    btnSeparater.setVisibility(View.GONE);
                }
                if (TextUtils.isEmpty(leftBtnText)) {
                    //只有一个按钮时设置圆角背景
                    rightBtn.setBackgroundResource(R.drawable.btn_selector);
                } else if (TextUtils.isEmpty(rightBtnText)) {
                    //只有一个按钮时设置圆角背景
                    leftBtn.setBackgroundResource(R.drawable.btn_selector);
                }
            } else {
                btnRLView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.custom_dialog_leftbtn:
                    btnClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                    dialog.dismiss();
                    break;
                case R.id.custom_dialog_rightbtn:
                    btnClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    dialog.dismiss();
                    break;

                default:
                    break;
            }
        }
    }
}

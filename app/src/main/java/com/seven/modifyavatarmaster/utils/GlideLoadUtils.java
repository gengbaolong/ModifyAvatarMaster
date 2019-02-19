package com.seven.modifyavatarmaster.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;


/**
 * Created : 2018/11/26 11:22
 * Description :
 * Author : gengbaolong
 */

public class GlideLoadUtils {

    /**
     * 借助内部类 实现线程安全的单例模式
     * 属于懒汉式单例，因为Java机制规定，内部类SingletonHolder只有在getInstance()
     * 方法第一次调用的时候才会被加载（实现了lazy），而且其加载过程是线程安全的。
     * 内部类加载的时候实例化一次instance。
     */
    public GlideLoadUtils() {
    }

    private static class GlideLoadUtilsHolder {
        private final static GlideLoadUtils INSTANCE = new GlideLoadUtils();
    }

    public static GlideLoadUtils getInstance() {
        return GlideLoadUtilsHolder.INSTANCE;
    }

    /**
     * 根据返回的图片url显示图片
     * @param context
     * @param imageView
     * @param default_image
     */
    public void glideLoadFromImageId(Context context, String imageUrl, final ImageView imageView, int default_image) {
        if (context != null) {
            Glide.with(context).load(imageUrl).dontAnimate().skipMemoryCache(false).priority(Priority.LOW).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void glideRoundCornerLoadFromImageId(final Activity activity, String imageUrl, final ImageView imageView, int default_image, final int corner) {
        if (activity != null) {
            if (!activity.isDestroyed()) {
                Glide.with(activity).load(imageUrl).asBitmap().placeholder(default_image).error(default_image).centerCrop().into(new BitmapImageViewTarget(imageView){
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(DensityUtil.dip2px(activity, corner));
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
            }
        }
    }


    /**
     * Glide 加载 简单判空封装 防止异步加载数据时调用Glide 抛出异常
     *
     * @param context
     * @param url           加载图片的url地址  String
     * @param imageView     加载图片的ImageView 控件
     * @param default_image 图片展示错误的本地图片 id
     */
    public void glideLoad(Context context, String url, final ImageView imageView, int default_image) {
        if (context != null) {
            Glide.with(context).load(url).dontAnimate().skipMemoryCache(false).priority(Priority.LOW).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void glideLoad(final Activity activity, String url, final ImageView imageView, int default_image) {//加载图片时添加监听
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).placeholder(default_image).dontAnimate().listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    return false;
                }
            }).into(imageView);
        }
    }

    /**
     * glide加载头像(圆形)
     * @param activity
     * @param url
     * @param imageView
     * @param default_image
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void glideAvatarLoad(final Activity activity, String url, final ImageView imageView, int default_image) {
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).asBitmap().placeholder(default_image).error(default_image).centerCrop().into(new BitmapImageViewTarget(imageView){
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imageView.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
    }

    public void glideLoad(Fragment fragment, String url, ImageView imageView, int default_image) {
        if (fragment != null && fragment.getActivity() != null) {
            Glide.with(fragment).load(url).placeholder(default_image).into(imageView);
        }
    }

    public void glideLoad(android.app.Fragment fragment, String url, ImageView imageView, int default_image) {
        if (fragment != null && fragment.getActivity() != null) {
            Glide.with(fragment).load(url).placeholder(default_image).into(imageView);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void glideRoundCorner(final Activity activity, String url, final ImageView imageView, int default_image){
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).asBitmap().placeholder(default_image).error(default_image).centerCrop().into(new BitmapImageViewTarget(imageView){
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                    circularBitmapDrawable.setCornerRadius(10);
                    imageView.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
//        //设置图片圆角角度
//        RoundedCorners roundedCorners= new RoundedCorners(6);
////通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
//
//        Glide.with(context).load(files.getFilePath()).apply(options).into(mUserPhoto);
    }


}

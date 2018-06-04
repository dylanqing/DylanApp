package com.worldunion.dylanapp.module.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.worldunion.dylanapp.R;

/**
 * @author Dylan
 * @time 2016/11/8 11:09
 * @des Glide处理类
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class GlideImage implements IDisplay {

    private static RequestManager manager;

    /**
     * 显示图片
     *
     * @param url
     * @param imageView
     */
    @Override
    public void show(String url, ImageView imageView, int placeHolder, int errorHolder) {
        manager.load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .placeholder(placeHolder)
                .error(errorHolder)
                .into(imageView);
    }

    /**
     * 显示图片
     *
     * @param url
     * @param imageView
     * @param errorHolder
     */
    @Override
    public void show(String url, ImageView imageView, int errorHolder) {
        manager.load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .placeholder(R.drawable.image_placeholder_200_150)
                .error(errorHolder)
                .into(imageView);
    }

    /**
     * 默认占位图和错误图
     *
     * @param url
     * @param imageView
     */
    @Override
    public void show(String url, ImageView imageView) {
        manager.load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .placeholder(R.drawable.image_placeholder_200_150)
                .error(R.drawable.image_error_200_150)
                .into(imageView);
    }

    /**
     * 没有占位图
     *
     * @param url
     * @param imageView
     */
    @Override
    public void showNoHolder(String url, ImageView imageView) {
        manager.load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(imageView);
    }

    /**
     * 不缓存图片
     *
     * @param url
     * @param imageView
     */
    @Override
    public void showNoneCache(String url, ImageView imageView) {
        manager.load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .crossFade()
                .placeholder(R.drawable.image_placeholder_200_150)
                .error(R.drawable.image_error_200_150)
                .into(imageView);
    }


    /**
     * 初始化
     *
     * @param context
     */
    @Override
    public void init(Context context) {
        manager = Glide.with(context);
    }


    /**
     * 当列表在滑动的时候，调用pauseRequests()取消请求，滑动停止时，调用resumeRequests()恢复请求。
     */
    @Override
    public void resumeRequest() {
        manager.resumeRequests();
    }

    /**
     * 当列表在滑动的时候，调用pauseRequests()取消请求，滑动停止时，调用resumeRequests()恢复请求。
     */
    @Override
    public void pauseRequest() {
        manager.pauseRequests();
    }

    /**
     * 清除磁盘缓存---需要在子线程中执行
     *
     * @param context
     */
    @Override
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 清除内存缓存---需要在主线程执行
     *
     * @param context
     */
    @Override
    public void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();

    }

    /**
     * 获取缓存大小
     *
     * @param context
     */
    public void getCacheSize(Context context) {
        Glide.get(context).clearMemory();
    }


}

package com.worldunion.dylanapp.module.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dylan.baselib.utils.log.KLog;

import java.io.File;

/**
 * @author Dylan
 * @time 2016/11/8 16:39
 * @des 图片加载工具类
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class ImageUtils {

    private static String TAG = "ImageUtils2";

    public static IDisplay display = null;

    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context) {
        display = new GlideImage();
        display.init(context);
    }

    /**
     * 显示图片
     *
     * @param url
     * @param imageView
     */
    public static void show(String url, ImageView imageView) {
        display.show(url, imageView);
    }

    /**
     * 显示图片
     *
     * @param url
     * @param imageView
     * @param errorHolder 错误图
     */
    public static void show(String url, ImageView imageView, int errorHolder) {
        display.show(url, imageView, errorHolder);
    }

    /**
     * 显示图片
     *
     * @param url
     * @param imageView
     * @param placeHolder 占位图
     * @param errorHolder 错误图
     */
    public static void show(String url, ImageView imageView, int placeHolder, int errorHolder) {
        display.show(url, imageView, placeHolder, errorHolder);
    }


    /**
     * @param url
     * @param imageView
     */
    public static void showNoHolder(String url, ImageView imageView) {
        display.showNoHolder(url, imageView);
    }

    /**
     * 无缓存
     *
     * @param url
     * @param imageView
     */
    public static void showNoneCache(String url, ImageView imageView) {
        display.showNoneCache(url, imageView);
    }


    /**
     * 当列表在滑动的时候，调用pauseRequests()取消请求，滑动停止时，调用resumeRequests()恢复请求。
     */
    public static void resumeRequest() {
        display.resumeRequest();
    }

    /**
     * 当列表在滑动的时候，调用pauseRequests()取消请求，滑动停止时，调用resumeRequests()恢复请求。
     */
    public static void pauseRequests() {
        display.pauseRequest();
    }

    /**
     * 获取缓存路径
     *
     * @param context
     */
    public static void getCacheFilePath(Context context) {
        KLog.init(true);
        File photoCacheDir = Glide.getPhotoCacheDir(context);
        KLog.d(TAG, "photoCacheDir = " + photoCacheDir.getAbsolutePath());
    }

}

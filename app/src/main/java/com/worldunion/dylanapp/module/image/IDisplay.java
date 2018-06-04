package com.worldunion.dylanapp.module.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * @author Dylan
 * @time 2016/11/8 11:06
 * @des 图片处理上层接口
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public interface IDisplay {

    void show(String url, ImageView imageView, int placeHolder, int errorHolder);

    void show(String url, ImageView imageView, int errorHolder);

    void show(String url, ImageView imageView);

    void showNoHolder(String url, ImageView imageView);

    void showNoneCache(String url, ImageView imageView);

    void init(Context context);

    void resumeRequest();

    void pauseRequest();

    void clearMemoryCache(Context context);

    void clearDiskCache(Context context);

}

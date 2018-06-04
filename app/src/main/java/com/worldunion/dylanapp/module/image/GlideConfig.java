package com.worldunion.dylanapp.module.image;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.ViewTarget;
import com.worldunion.dylanapp.R;

/**
 * @author Dylan
 * @time 2016/11/8 16:50
 * @des Glide配置
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class GlideConfig implements GlideModule {

    int diskSize = 1024 * 1024 * 100;
    int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;  // 取1/8最大内存作为最大缓存

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 定义缓存大小和位置
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "image_cache", diskSize));  //内存中
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "image_cache", diskSize)); //sd卡中

        // 默认内存和图片池大小
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize(); // 默认内存大小
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize(); // 默认图片池大小
        builder.setMemoryCache(new LruResourceCache(defaultMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(defaultBitmapPoolSize));

        // 自定义内存和图片池大小
        builder.setMemoryCache(new LruResourceCache(memorySize));
        builder.setBitmapPool(new LruBitmapPool(memorySize));

        // 定义图片格式
        //builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565); // 默认

        ViewTarget.setTagId(R.id.glide_tag_id);//设置tag

    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }


}

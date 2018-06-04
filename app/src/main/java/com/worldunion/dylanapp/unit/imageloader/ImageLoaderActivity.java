package com.worldunion.dylanapp.unit.imageloader;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.BaseMvpActivity;

/**
 * Created by Dylan on 2017/9/14.
 * ImageLoader测试页面
 */

public class ImageLoaderActivity extends BaseMvpActivity {

    private ImageView mIvImage;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_imageloader;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void init() {


        mIvImage = (ImageView) findViewById(R.id.ivImage);

        //ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(this);

        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(this);
//        builder.diskCache(DiskCache)
        ImageLoaderConfiguration build = builder.writeDebugLogs().build();


        ImageLoader.getInstance().init(build);

        //String url = "http://misc.misc.testing.jrq.com/uploads/image/FULAI/2017/09/07/59b111ab62f50phpOvLCZl.jpeg";
        String url = "drawable://R.drawable.sample_arc";
        ImageLoader instance = ImageLoader.getInstance();

        instance.displayImage(url, mIvImage);
    }

    @Override
    protected void getData() {

    }
}

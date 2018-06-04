package com.worldunion.dylanapp.module.http;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.worldunion.dylanapp.R;


/**
 * 网络加载的时候的加载中
 */
public class HttpProgress extends Dialog {

    public HttpProgress(Context context) {
        super(context);
    }

    public HttpProgress(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.loading_view);
        // 获取ImageView上的动画背景
        AnimationDrawable spinner = (AnimationDrawable) imageView.getDrawable();
        // 开始动画
        spinner.start();
    }

    @Override
    protected void onStop() {
        ImageView imageView = (ImageView) findViewById(R.id.loading_view);
        if (imageView != null) {
            imageView.clearAnimation();
        }
        super.onStop();
    }

    /**
     * 给Dialog设置提示信息
     *
     * @param message
     */
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.loading_msg).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.loading_msg);
            txt.setText(message);
            txt.invalidate();
        }
    }

    public static HttpProgress show(Context context, boolean cancelable) {
        return show(context, "加载中...", cancelable, null);
    }

    public static HttpProgress show(Context context, boolean cancelable, OnCancelListener cancelListener) {
        return show(context, "加载中...", cancelable, cancelListener);
    }


    /**
     * 弹出自定义ProgressDialog
     *
     * @param context        上下文
     * @param message        提示
     * @param cancelable     是否按返回键取消
     * @param cancelListener dismiss监听
     * @return
     */
    public static HttpProgress show(Context context, CharSequence message, boolean cancelable, OnCancelListener cancelListener) {
        HttpProgress dialog = new HttpProgress(context, R.style.http_progress);
        dialog.setTitle("");
        dialog.setContentView(R.layout.http_loading_layout);
        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.loading_msg).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.loading_msg);
            txt.setText(message);
        }
        // 按返回键是否取消
        dialog.setCancelable(cancelable);
        // 监听dismiss处理
        dialog.setOnCancelListener(cancelListener);

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.0f;
        // 设置居中
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.show();
        return dialog;
    }

    public static void close(HttpProgress dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}
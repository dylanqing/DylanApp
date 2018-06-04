package com.worldunion.dylanapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.worldunion.dylanapp.R;


/**
 * 自定义加载错误处理的页面 (加载中 无数据 加载失败 显示内容）
 */
public class LoadingLayout extends FrameLayout {

    private int emptyView, errorView, loadingView;
    private OnClickListener onRetryClickListener;

    public LoadingLayout(Context context) {
        this(context, null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingLayout, 0, 0);
        try {
            emptyView = a.getResourceId(R.styleable.LoadingLayout_emptyView, R.layout.loading_layout_empty);
            errorView = a.getResourceId(R.styleable.LoadingLayout_errorView, R.layout.loading_layout_error);
            loadingView = a.getResourceId(R.styleable.LoadingLayout_loadingView, R.layout.loading_layout_load);

            LayoutInflater inflater = LayoutInflater.from(getContext());
            //因为empty页面有很多种 替换布局很麻烦 所以增加empty页面自定义图片和文字
            View emptyLayout = inflater.inflate(emptyView, this, true);
            ImageView emptyImg = (ImageView) emptyLayout.findViewById(R.id.empty_img);
            TextView emptyTxt = (TextView) emptyLayout.findViewById(R.id.btn_empty_retry);
            Drawable emptyDrawable = a.getDrawable(R.styleable.LoadingLayout_emptyImg);
            String emptyString = a.getString(R.styleable.LoadingLayout_emptyTxt);
            if (emptyDrawable != null && emptyImg != null) {
                emptyImg.setImageDrawable(emptyDrawable);
            }
            if (!TextUtils.isEmpty(emptyString) && emptyTxt != null) {
                emptyTxt.setText(emptyString);
            }

            inflater.inflate(errorView, this, true);
            inflater.inflate(loadingView, this, true);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        for (int i = 0; i < getChildCount() - 1; i++) {
            getChildAt(i).setVisibility(GONE);
        }

//        if (findViewById(R.id.btn_empty_retry) != null) {
//            findViewById(R.id.btn_empty_retry).setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (null != onRetryClickListener) {
//                        onRetryClickListener.onClick(v);
//                    }
//                }
//            });
//        }

        if (findViewById(R.id.btn_error_retry) != null) {
            findViewById(R.id.btn_error_retry).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onRetryClickListener) {
                        onRetryClickListener.onClick(v);
                    }
                }
            });
        }
    }

    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.onRetryClickListener = onRetryClickListener;
    }

    public void showEmpty() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 0) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    public void showError() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 1) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    public void showLoading() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 2) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    public void showContent() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 3) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }
}
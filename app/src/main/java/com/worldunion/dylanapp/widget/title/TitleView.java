package com.worldunion.dylanapp.widget.title;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.worldunion.dylanapp.R;


/**
 * My app title
 **/
public class TitleView extends LinearLayout {
    private OnLeftViewListener mLeftViewListener;
    private OnRightViewListener mRightViewListener;
    private OnRightView2Listener mRightView2Listener;
    private MyViewHolder mViewHolder;
    private View viewAppTitle;
    private String mLeftDesc;
    private String mRightDesc;
    private String mCenterDesc;
    private Drawable mLeftDrawable;

    public void setRightTxtColor(int mRightTxtColor) {
        mViewHolder.llRight.setTextColor(mRightTxtColor);
        if (mRightTxtColor == getContext().getResources().getColor(R.color.main_color)) {// 黑色可点击
            mViewHolder.llRight.setEnabled(true);
        } else {// 灰色不可以点击
            mViewHolder.llRight.setEnabled(false);
        }
    }

    public String getmCenterDesc() {
        return mCenterDesc;
    }

    public void setmCenterDesc(String mCenterDesc) {
        this.mCenterDesc = mCenterDesc;
        mViewHolder.centView.setText(mCenterDesc);
    }

    public void setmLeftDrawable(Drawable mLeftDrawable) {
        this.mLeftDrawable = mLeftDrawable;
        mViewHolder.leftView.setImgDrawable(mLeftDrawable);
    }

    private Drawable mRightDrawable;
    private Drawable mRightDrawable2;

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttrs(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public TitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        readAttrs(context, attrs);
        init();
    }

    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.TitleView);
        mLeftDesc = ta.getString(R.styleable.TitleView_leftDesc);
        mCenterDesc = ta.getString(R.styleable.TitleView_centerDesc);
        mRightDesc = ta.getString(R.styleable.TitleView_rightDesc);
        mLeftDrawable = ta.getDrawable(R.styleable.TitleView_leftImage);
        mRightDrawable = ta.getDrawable(R.styleable.TitleView_rightImage);
        mRightDrawable2 = ta.getDrawable(R.styleable.TitleView_rightImage2);
        ta.recycle();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        viewAppTitle = inflater.inflate(R.layout.common_title, null);
        this.addView(viewAppTitle, layoutParams);
        mViewHolder = new MyViewHolder(this);
        if (!TextUtils.isEmpty(mLeftDesc)) {
            mViewHolder.leftView.setText(mLeftDesc);
        }
        if (!TextUtils.isEmpty(mCenterDesc)) {
            mViewHolder.centView.setText(mCenterDesc);
        }
        if (!TextUtils.isEmpty(mRightDesc)) {
            mViewHolder.llRight.setText(mRightDesc);
        }
        if (mLeftDrawable != null) {
            mViewHolder.leftView.setImgDrawable(mLeftDrawable);
        }
        if (mRightDrawable != null) {
            mViewHolder.llRight.setImgDrawable(mRightDrawable);
        }
        if (mRightDrawable2 != null) {
            mViewHolder.llRight2.setImgDrawable(mRightDrawable2);
        }

        mViewHolder.leftView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (Utility.isFastDoubleClick())
                // {
                // return;
                // }

                if (mLeftViewListener != null) {
                    mLeftViewListener.onLeftViewonClick(v);
                }
            }
        });
        mViewHolder.llRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (Utility.isFastDoubleClick())
                // {
                // return;
                // }

                if (mRightViewListener != null) {
                    mRightViewListener.OnRightViewonClick(v);
                }
            }
        });
        mViewHolder.llRight2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRightView2Listener != null) {
                    mRightView2Listener.OnRightView2onClick(v);
                }
            }
        });
    }

    public void setOnLeftViewListener(OnLeftViewListener listen) {
        mLeftViewListener = listen;
    }

    public void setOnRightViewListener(OnRightViewListener listen) {
        mRightViewListener = listen;
    }

    public void setOnRightView2Listener(OnRightView2Listener listen) {
        mRightView2Listener = listen;
    }

    public void setOnRightView2Img(int imgId) {
        mViewHolder.llRight2.setImgResource(imgId);
    }

    public static abstract interface OnLeftViewListener {
        public abstract void onLeftViewonClick(View v);
    }

    public static abstract interface OnRightViewListener {
        public abstract void OnRightViewonClick(View v);
    }

    public static abstract interface OnRightView2Listener {
        public abstract void OnRightView2onClick(View v);
    }

    static class MyViewHolder {
        ImgTxtButton leftView;
        TextView centView;
        ImgTxtButton llRight;
        ImgTxtButton llRight2;

        public MyViewHolder(View v) {
            leftView = (ImgTxtButton) v.findViewById(R.id.view_left);
            centView = (TextView) v.findViewById(R.id.view_center);
            llRight = (ImgTxtButton) v.findViewById(R.id.view_right);
            llRight2 = (ImgTxtButton) v.findViewById(R.id.view_right2);
        }
    }

}

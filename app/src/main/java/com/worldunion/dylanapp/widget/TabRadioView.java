package com.worldunion.dylanapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.worldunion.dylanapp.R;


/**
 * 首页底部按钮
 */
public class TabRadioView extends RelativeLayout {
    private TextView text;
    private int colorSelectRes;
    private int colorUnSelectRes;

    private ImageView img;
    private int imgSelectRes;
    private int imgUnSelectRes;

    public boolean isCheck = false;

    public TabRadioView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public TabRadioView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View contentView = View.inflate(context, R.layout.common_tab_view, null);
        text = (TextView) contentView.findViewById(R.id.tab_radio_txt);
        img = (ImageView) contentView.findViewById(R.id.tab_radio_img);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabRadioView);
        text.setText(getText(a, R.styleable.TabRadioView_text));
        colorSelectRes = getResources().getColor(a.getResourceId(R.styleable.TabRadioView_colorOn, R.color.main_color));
        colorUnSelectRes = getResources().getColor(a.getResourceId(R.styleable.TabRadioView_colorOff, R.color.grey_txt_color));
        text.setTextColor(colorUnSelectRes);
        text.setEnabled(false);

        imgSelectRes = a.getResourceId(R.styleable.TabRadioView_imgOn, R.drawable.common_tab_trans_bg);
        imgUnSelectRes = a.getResourceId(R.styleable.TabRadioView_imgOff, R.drawable.common_tab_trans_bg);
        img.setImageResource(imgUnSelectRes);
        addView(contentView);
        a.recycle();
    }

    private String getText(TypedArray a, int index) {
        String txt = a.getString(index);
        if (txt == null) {
            return "";
        }
        return txt;
    }

    /**
     * 设置状态
     *
     * @param check
     * @return
     * @updateTime 2015-6-27,下午10:42:37
     * @updateAuthor qw
     */
    public void setCheck(boolean check) {
        if (isCheck == check) {
            return;
        }
        isCheck = check;
        text.setEnabled(check);
        img.setImageResource(isCheck ? imgSelectRes : imgUnSelectRes);
        if (check) {
            text.setTextColor(colorSelectRes);
        } else {
            text.setTextColor(colorUnSelectRes);
        }
    }

    /**
     * 设置文本
     */
    public void setText(String value) {
        text.setText(value);
    }

    /**
     * 设置图片
     */
    public void setImgRes(int imgSelectRes, int imgUnSelectRes) {
        this.imgSelectRes = imgSelectRes;
        this.imgUnSelectRes = imgUnSelectRes;
        img.setImageResource(isCheck ? imgSelectRes : imgUnSelectRes);
    }

    /**
     * 设置文字颜色
     */
    public void setColorRes(int colorSelectRes, int colorUnSelectRes) {
        this.colorSelectRes = colorSelectRes;
        this.colorUnSelectRes = colorUnSelectRes;
        img.setImageResource(isCheck ? colorSelectRes : colorUnSelectRes);
    }
}
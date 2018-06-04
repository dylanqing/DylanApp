package com.worldunion.dylanapp.widget;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.worldunion.dylanapp.R;


/**
 * @author Dylan
 * @time 2016/11/21 14:00
 * @des 倒计时
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class TimeCount extends CountDownTimer {

    public TextView mButton;

    /**
     * @param millisInFuture    总时长
     * @param countDownInterval 计时时间间隔
     * @param mButton
     */
    public TimeCount(long millisInFuture, long countDownInterval, TextView mButton) {
        super(millisInFuture, countDownInterval);
        this.mButton = mButton;
    }

    /**
     * 计时完毕时触发
     */
    public void onFinish() {
        setViewProperty();
    }

    /**
     * 计时过程显示
     *
     * @param millisUntilFinished
     */
    @Override
    public void onTick(long millisUntilFinished) {
        mButton.setClickable(false);
        mButton.setTextColor(Color.WHITE);
        mButton.setBackgroundResource(R.drawable.shape_regist_bg_count);
        mButton.setText("已发送(" + millisUntilFinished / 1000 + "s)");
    }


    public void setViewProperty() {
        this.cancel();
        mButton.setText("重新验证");
        mButton.setTextColor(Color.parseColor("#4ec2cd"));
        mButton.setBackgroundResource(R.drawable.shape_regist_bg);
        mButton.setClickable(true);
    }
}

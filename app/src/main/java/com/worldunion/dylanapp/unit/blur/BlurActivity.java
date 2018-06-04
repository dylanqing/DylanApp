package com.worldunion.dylanapp.unit.blur;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;

import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.BaseMvpActivity;
import com.worldunion.dylanapp.utils.StatusBarUtil;
import com.worldunion.dylanapp.widget.BlurBitmap;

import butterknife.BindView;


/**
 * @author Dylan
 * @time 2017/3/27 11:05
 * @des 高斯模糊Activity, 一个渐变高斯模糊的动画效果
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class BlurActivity extends BaseMvpActivity {

    @BindView(R.id.iv_login_bg_finish)
    ImageView mIvLoginBgFinish;
    @BindView(R.id.iv_login_bg)
    ImageView mIvLoginBg;

    //动画是否完成可以登录，false不可以，true可以
    private boolean canLogin = false;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_blur;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void init() {
        doLoginAnim(this, mIvLoginBg, mIvLoginBgFinish);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setStatusBar(View view) {
        super.setStatusBar(view);
        StatusBarUtil.setTransparent(this);
        ViewCompat.setFitsSystemWindows(view, false);
    }

    /**
     * 登录页面背景动画
     *
     * @param context
     * @param originView 原始图
     * @param finalView  高斯模糊图
     */
    public void doLoginAnim(final Context context, final ImageView originView, final ImageView finalView) {
        canLogin = false;
        /*缩放*/
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(originView, "scaleX", 1.5f, 1.0f).setDuration(1000);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(originView, "scaleY", 1.5f, 1.0f).setDuration(1000);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /*渐变高斯*/
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.login_bg_image);
                finalView.setImageBitmap(new BlurBitmap().blur(context, bitmap));
                ValueAnimator valueAnimator = new ValueAnimator();
                valueAnimator.setObjectValues(1f, 0f);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        originView.setAlpha(value);
                    }
                });
                valueAnimator.setDuration(700);
                valueAnimator.start();
                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        canLogin = true;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public static void launch(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, BlurActivity.class);
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }
    }

}

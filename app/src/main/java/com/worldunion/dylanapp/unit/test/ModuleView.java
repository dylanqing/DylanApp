package com.worldunion.dylanapp.unit.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dylan.baselib.utils.log.KLog;

/**
 * @author Dylan
 * @time 2017/4/21 16:24
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class ModuleView extends View {

    private Paint mPaint;

    public ModuleView(Context context) {
        super(context);
        init();
    }

    public ModuleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ModuleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(1.5f);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KLog.d("ModuleView ---- onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        KLog.d("ModuleView ---- dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

}

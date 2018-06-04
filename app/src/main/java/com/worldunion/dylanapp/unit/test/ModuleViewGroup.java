package com.worldunion.dylanapp.unit.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.dylan.baselib.utils.log.KLog;

/**
 * @author Dylan
 * @time 2017/4/21 16:26
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class ModuleViewGroup extends LinearLayout {


    public ModuleViewGroup(Context context) {
        super(context);
    }

    public ModuleViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ModuleViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        KLog.d("ModuleViewGroup ---- onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KLog.d("ModuleViewGroup ---- dispatchTouchEvent");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KLog.d("ModuleViewGroup ---- onTouchEvent");
        return super.onTouchEvent(event);
    }

}

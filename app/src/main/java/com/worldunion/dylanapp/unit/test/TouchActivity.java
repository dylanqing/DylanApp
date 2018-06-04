package com.worldunion.dylanapp.unit.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.worldunion.dylanapp.R;
import com.dylan.baselib.utils.log.KLog;

/**
 * @author Dylan
 * @time 2017/4/21 16:23
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class TouchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KLog.d("TouchActivity ---- onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KLog.d("TouchActivity ---- dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }


}

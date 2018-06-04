package com.worldunion.dylanapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.utils.CommonUtils;
import com.dylan.baselib.utils.log.KLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Dylan
 * @time 2016/12/15 11:04
 * @des 选择时间对话框
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class SelectTimeDialog {

    private Dialog mDialog;
    private List<String> mYearList = new ArrayList<>();
    private List<String> mMonthList = new ArrayList<>();
    private List<String> mDayList = new ArrayList<>();
    private TextView mTvConfirm;

    private String year;
    private String month;
    private String day;
    private WheelView mWvYear;
    private WheelView mWvMonth;
    private WheelView mWvDay;

    private String selectDate;

    public void init(Context context, String date) {
        this.selectDate = date;
        mDialog = new Dialog(context, R.style.CommonDialog);
        mDialog.show();

        View view = View.inflate(context, R.layout.item_dialog_select_time, null);

        TextView tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        mTvConfirm = (TextView) view.findViewById(R.id.tvConfirm);

        mWvYear = (WheelView) view.findViewById(R.id.wvYear);
        mWvMonth = (WheelView) view.findViewById(R.id.wvMonth);
        mWvDay = (WheelView) view.findViewById(R.id.wvDay);

        try {
            initData();
        } catch (Exception e) {
            new Throwable("data error");
        }

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);

        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = (int) (CommonUtils.getScreenWidth(context) * 0.8);
        // params.height = (int) (UIUtils.getScreenHeight() * 0.7);

        Window window = mDialog.getWindow();
        window.setContentView(view);
        window.setAttributes(params);
        window.setGravity(Gravity.CENTER);
        mDialog.hide();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        /*根据系统时间获取数据，初始化wheel内部数据*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        String[] split = format.split("-");
        Integer yearNow = Integer.valueOf(split[0]);
        /*年*/
        for (int i = 0; i < 50; i++) {
            int item = yearNow - i;
            mYearList.add("" + item);
        }
        /*月*/
        for (int i = 1; i < 13; i++) {
            if (i < 10) {
                mMonthList.add("0" + i + "");
            } else {
                mMonthList.add(i + "");
            }
        }
        /*日*/
        for (int i = 1; i <= 31; i++) {
            if (i < 10) {
                mDayList.add("0" + i + "");
            } else {
                mDayList.add(i + "");
            }
        }

        /*根据传入的时间选中对应的item*/
        String[] current = selectDate.split("-");

        year = current[0];
        month = current[1];
        day = current[2];

        mWvYear.setSeletion(yearNow - Integer.valueOf(current[0]));
        mWvMonth.setSeletion(Integer.valueOf(current[1]) - 1);
        mWvDay.setSeletion(Integer.valueOf(current[2]) - 1);


        mWvYear.setItems(mYearList);
        mWvMonth.setItems(mMonthList);
        mWvDay.setItems(mDayList);
    }

    public void show(View.OnClickListener listener) {
        if (mDialog.isShowing()) {
            mDialog.hide();
        }

        /*数据返回监听*/
        mWvYear.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                year = item;
                if (onSelectListener != null) {
                    onSelectListener.onSelect(year, month, day);
                }
                KLog.d("year = " + year);
            }
        });
        mWvMonth.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                month = item;
                if (onSelectListener != null) {
                    onSelectListener.onSelect(year, month, day);
                }
                KLog.d("month = " + month);
            }
        });

        mWvDay.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                day = item;
                if (onSelectListener != null) {
                    onSelectListener.onSelect(year, month, day);
                }
                KLog.d("day = " + day);
            }
        });

        if (listener != null) {
            mTvConfirm.setOnClickListener(listener);
        }
        /*第一次触发一下*/
        if (onSelectListener != null) {
            onSelectListener.onSelect(year, month, day);
        }
        mDialog.show();
    }

    public void hide() {
        if (mDialog.isShowing()) {
            mDialog.hide();
        }
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    /*对外暴露监听结果*/
    public OnWheelSelectListener onSelectListener;

    public interface OnWheelSelectListener {
        void onSelect(String year, String month, String day);
    }

    /**
     * 监听结果
     *
     * @param listener 结果监听
     */
    public void setOnWheelSelectListener(OnWheelSelectListener listener) {
        onSelectListener = listener;
    }
}

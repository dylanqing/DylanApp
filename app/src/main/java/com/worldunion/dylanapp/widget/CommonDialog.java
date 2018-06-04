package com.worldunion.dylanapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.utils.CommonUtils;


/**
 * @author Dylan
 * @time 2016/9/18 11:37
 * @des 通用对话框
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class CommonDialog {

    private TextView mTvTitle;
    private TextView mTvDesc;
    private TextView mTvConfirm;
    private TextView mTvCancel;
    private Dialog mDialog;

    public CommonDialog() {
    }

    public void init(Context context, String title, String desc, String cancel, String confirm) {
        init(context, title, desc, cancel, confirm, true);
    }

    public void init(Context context, String title, String desc, String cancel, String confirm, boolean outside) {

        mDialog = new Dialog(context, R.style.CommonDialog);
        mDialog.show();

        View view = View.inflate(context, R.layout.common_dialog, null);
        mTvTitle = (TextView) view.findViewById(R.id.txtTitle);
        mTvDesc = (TextView) view.findViewById(R.id.txtDesc);
        mTvConfirm = (TextView) view.findViewById(R.id.txtConfirm);
        mTvCancel = (TextView) view.findViewById(R.id.txtCancel);

        // 设置txt数据，如果为空，说明不需要显示，则设置不可见
        mTvTitle.setText(title);
        mTvTitle.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);

        mTvDesc.setText(desc);
        mTvDesc.setVisibility(TextUtils.isEmpty(desc) ? View.GONE : View.VISIBLE);

        mTvConfirm.setText(confirm);
        mTvConfirm.setVisibility(TextUtils.isEmpty(confirm) ? View.GONE : View.VISIBLE);

        mTvCancel.setText(cancel);
        mTvCancel.setVisibility(TextUtils.isEmpty(cancel) ? View.GONE : View.VISIBLE);

        mDialog.setCanceledOnTouchOutside(outside);
        mDialog.setCancelable(outside);
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = (int) (CommonUtils.getScreenWidth(context) * 0.8);
        //		params.height = (int) (UIUtils.getScreenHeight() * 0.7);

        Window window = mDialog.getWindow();
        window.setContentView(view);
        window.setAttributes(params);
        mDialog.dismiss();
    }

    /**
     * @param confirmListener 确定
     * @param cancelListener  取消
     */
    public void show(View.OnClickListener confirmListener, View.OnClickListener cancelListener) {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mTvConfirm != null && confirmListener != null) {
            mTvConfirm.setOnClickListener(confirmListener);
        } else {
            mTvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        if (mTvCancel != null && cancelListener != null) {
            mTvCancel.setOnClickListener(cancelListener);
        } else {
            mTvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
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

    public static class Builder {

        private String cancelStr;
        private String confirmStr;
        private String titleStr;
        private String descStr;
        private boolean cancelOutside;

        public Builder() {

        }

        public Builder setTitleStr(String data) {
            titleStr = data;
            return this;
        }

        public Builder setCancelStr(String data) {
            cancelStr = data;
            return this;
        }

        public Builder setDescStr(String data) {
            descStr = data;
            return this;
        }


        public Builder setConfirmStr(String data) {
            confirmStr = data;
            return this;
        }

        public Builder setCancelOutSide(boolean flag) {
            cancelOutside = flag;
            return this;
        }

        public CommonDialog build(Context context) {
            CommonDialog commonDialog = new CommonDialog();
            commonDialog.init(context, titleStr, descStr, cancelStr, confirmStr, cancelOutside);
            return commonDialog;
        }

    }

    /**
     * 强制更新隐藏取消按钮
     */
    public void setForceUpdate() {
        mTvCancel.setVisibility(View.GONE);
    }
}

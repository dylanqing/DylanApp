package com.worldunion.dylanapp.module.http;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;

import com.lzy.okgo.request.BaseRequest;

/**
 * 对于网络请求是否需要弹出进度对话框
 * @param <T>
 */
public abstract class BeanCallback<T> extends JsonCallback<T> {

    /**
     * waitDialog.
     */
    private HttpProgress mWaitDialog;

    private Context context;

    public BeanCallback() {
        super();
    }

    public BeanCallback(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        //网络请求前显示对话框
        if (context != null) {
            mWaitDialog = HttpProgress.show(context, false, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    //暂未发现可以取消请求
                }
            });
        }
    }

    @Override
    public void onAfter(@Nullable T t, @Nullable Exception e) {
        super.onAfter(t, e);
        //网络请求结束后关闭对话框
        if (context != null && mWaitDialog != null && mWaitDialog.isShowing())
            mWaitDialog.dismiss();
    }
}

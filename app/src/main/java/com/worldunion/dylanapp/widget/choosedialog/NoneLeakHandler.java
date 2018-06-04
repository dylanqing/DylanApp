package com.worldunion.dylanapp.widget.choosedialog;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by alexlong on 15/12/9.
 * 不会导致内存泄露的handler
 */
public class NoneLeakHandler<T> extends Handler {

    private final WeakReference<T> objOut;

    public NoneLeakHandler(T obj) {
        objOut = new WeakReference<T>(obj);
    }

    /**
     * 用final修饰, 目的是不让子类去继承.
     */
    @Override
    public final void handleMessage(Message msg) {
        T obj = objOut.get();
        if (obj != null) {
            handleNoneLeakMsg(msg, obj);
        }
    }

    //处理message, 子类继承
    protected void handleNoneLeakMsg(Message msg, T obj) {
    }
}

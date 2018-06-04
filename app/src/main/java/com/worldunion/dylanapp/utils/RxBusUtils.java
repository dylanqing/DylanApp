package com.worldunion.dylanapp.utils;


import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subscribers.SerializedSubscriber;

/**
 * @author Dylan
 * @time 2016/6/6 11:00
 * @des rxBus工具类
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class RxBusUtils {

    private static volatile RxBusUtils defaultInstance;
    private final FlowableProcessor<Object> bus;

    static String TAG = "RxBusUtils";

    /*PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发送给观察者*/
    public RxBusUtils() {
        bus = PublishProcessor.create().toSerialized();
    }

    public static RxBusUtils getDefault() {
        RxBusUtils rxBus = defaultInstance;
        if (defaultInstance == null) {
            synchronized (RxBusUtils.class) {
                rxBus = defaultInstance;
                if (defaultInstance == null) {
                    rxBus = new RxBusUtils();
                    defaultInstance = rxBus;
                }
            }
        }
        return rxBus;
    }

    /*发送事件*/
    public void post(Object obj) {
        new SerializedSubscriber<>(bus).onNext(obj);
    }

    /**
     * 确定接收消息的类型
     *
     * @param aClass
     * @param <T>
     * @return
     */
    public <T> Flowable<T> toFlowable(Class<T> aClass) {
        return bus.ofType(aClass);
    }

    /**
     * 判断是否有订阅者
     *
     * @return
     */
    public boolean hasSubscribers() {
        return bus.hasSubscribers();
    }


}

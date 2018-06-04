package com.worldunion.dylanapp.module;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Dylan
 * @time 2017/2/24 15:24
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class common implements InvocationHandler {

    public Object target;

    public Object bind(Object target, Class[] interfaces) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.err.println("############我是JDK动态代理################");
        Object result = null;
        //反射方法前调用
        System.err.println("我准备说hello。");
        //反射执行方法  相当于调用target.sayHelllo;
        result = method.invoke(target, args);
        //反射方法后调用.
        System.err.println("我说过hello了");
        return result;
    }

}

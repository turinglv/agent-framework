package com.ktd.service.afw.boot.api.injector;

import com.ktd.service.afw.boot.api.injector.enhance.EnhancedMethodResult;
import java.lang.reflect.Method;

public interface StaticMethodAroundInjector extends Injector {
    /**
     * called before target method invocation.
     *
     * @param method
     * @param result change this result, if you want to truncate the method.
     */
    void beforeMethod(Class clazz, Method method, Object[] arguments, Class<?>[] argumentTypes, EnhancedMethodResult result);

    /**
     * called after target method invocation. Even method's invocation triggers an exception.
     *
     * @param method
     * @param ret    the method's original return value.
     * @return the method's actual return value.
     */
    default Object afterMethod(Class clazz, Method method, Object[] arguments, Class<?>[] argumentTypes, Object ret) {
        return ret;
    }

    /**
     * called when occur exception.
     *
     * @param method
     * @param t      the exception occur.
     */
    default void handleMethodException(Class clazz, Method method, Object[] arguments, Class<?>[] argumentTypes, Throwable t) {
        // TODO
    }
}

package com.ktd.service.afw.boot.api.injector;

import com.ktd.service.afw.boot.api.injector.enhance.EnhancedInstance;
import com.ktd.service.afw.boot.api.injector.enhance.EnhancedMethodResult;
import java.lang.reflect.Method;

public interface InstanceMethodAroundInjector extends Injector {
    /**
     * called before target method invocation.
     *
     * @param result change this result, if you want to truncate the method.
     * @throws Throwable
     */
    void beforeMethod(EnhancedInstance objInst, Method method, Object[] arguments, Class<?>[] argumentTypes, EnhancedMethodResult result) throws Throwable;

    /**
     * called after target method invocation. Even method's invocation triggers an exception.
     *
     * @param method
     * @param ret    the method's original return value.
     * @return the method's actual return value.
     * @throws Throwable
     */
    default Object afterMethod(EnhancedInstance objInst, Method method, Object[] arguments, Class<?>[] argumentTypes, Object ret) throws Throwable {
        return ret;
    }

    /**
     * called when occur exception.
     *
     * @param method
     * @param t      the exception occur.
     */
    default void handleMethodException(EnhancedInstance objInst, Method method, Object[] arguments, Class<?>[] argumentTypes, Throwable t) {
        // TODO fixme
    }
}

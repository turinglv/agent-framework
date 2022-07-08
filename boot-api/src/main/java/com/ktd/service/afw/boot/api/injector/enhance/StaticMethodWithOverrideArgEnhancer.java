package com.ktd.service.afw.boot.api.injector.enhance;

import com.ktd.service.afw.boot.api.injector.StaticMethodAroundInjector;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.boot.api.injector.InjectorInstanceFactory;
import java.lang.reflect.Method;
import ktd.bb.implementation.bind.annotation.AllArguments;
import ktd.bb.implementation.bind.annotation.Morph;
import ktd.bb.implementation.bind.annotation.Origin;
import ktd.bb.implementation.bind.annotation.RuntimeType;

public class StaticMethodWithOverrideArgEnhancer {

    /**
     * 日志输出
     */
    private static final Log logger = LogProvider.getBootLog();

    private String staticMethodsAroundInjectorClassName;

    public StaticMethodWithOverrideArgEnhancer(String staticMethodsAroundInjectorClassName) {
        this.staticMethodsAroundInjectorClassName = staticMethodsAroundInjectorClassName;
    }

    @RuntimeType
    public Object enhance(@Origin Class<?> clazz, @AllArguments Object[] allArguments, @Origin Method method, @Morph OverrideCallable zuper) throws Throwable {
        StaticMethodAroundInjector injector = InjectorInstanceFactory.create(staticMethodsAroundInjectorClassName, clazz.getClassLoader());
        if (injector != null && !injector.onOffState()) {
            return zuper.call(allArguments);
        }
        EnhancedMethodResult result = new EnhancedMethodResult();
        try {
            injector.beforeMethod(clazz, method, allArguments, method.getParameterTypes(), result);
        } catch (Throwable t) {
            logger.error("class[{}] before static method[{}] inject failure", clazz, method.getName(), t);
        }

        Object ret = null;
        try {
            if (!result.isContinue()) {
                ret = result.getDefineResult();
            } else {
                ret = zuper.call(allArguments);
            }
        } catch (Throwable t) {
            try {
                injector.handleMethodException(clazz, method, allArguments, method.getParameterTypes(), t);
            } catch (Throwable t2) {
                logger.error("class[{}] handle static method[{}] exception failure", clazz, method.getName(), t2.getMessage(), t2);
            }
            throw t;
        } finally {
            try {
                ret = injector.afterMethod(clazz, method, allArguments, method.getParameterTypes(), ret);
            } catch (Throwable t) {
                logger.error("class[{}] after static method[{}] inject failure:{}", clazz, method.getName(), t.getMessage(), t);
            }
        }
        return ret;
    }
}

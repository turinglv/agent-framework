package com.ktd.service.afw.boot.api.injector.enhance;

import com.ktd.service.afw.boot.api.exception.InjectorException;
import com.ktd.service.afw.boot.api.log.AgentErrorContext;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.boot.api.injector.InjectorInstanceFactory;
import com.ktd.service.afw.boot.api.injector.InstanceMethodAroundInjector;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import ktd.bb.implementation.bind.annotation.AllArguments;
import ktd.bb.implementation.bind.annotation.Origin;
import ktd.bb.implementation.bind.annotation.RuntimeType;
import ktd.bb.implementation.bind.annotation.SuperCall;
import ktd.bb.implementation.bind.annotation.This;

public class InstanceMethodEnhancer {

    /**
     * 日志输出
     */
    private static final Log LOG = LogProvider.getBootLog();

    private InstanceMethodAroundInjector injector;

    public InstanceMethodEnhancer(String instanceMethodsAroundInjectorClassName, ClassLoader targetClassLoader) {
        try {
            injector = InjectorInstanceFactory.create(instanceMethodsAroundInjectorClassName, targetClassLoader);
        } catch (Throwable t) {
            throw new InjectorException("Can't create InstanceMethodAroundInjector.", t);
        }
    }

    @RuntimeType
    public Object enhance(@This Object obj,
                          @AllArguments Object[] allArguments,
                          @Origin Method method,
                          @SuperCall Callable<?> zuper
    ) throws Throwable {

        if (injector == null || !injector.onOffState()) {
            return zuper.call();
        }

        EnhancedInstance targetObject = (EnhancedInstance) obj;
        EnhancedMethodResult result = new EnhancedMethodResult();

        try {
            injector.beforeMethod(targetObject, method, allArguments, method.getParameterTypes(), result);
        } catch (Throwable t) {
            LOG.error("class[{}] before method[{}] inject failure", obj.getClass(), method.getName(), t);
            AgentErrorContext.asyncReport(t, "调用Injector.beforeMethod发生异常:" + obj.getClass());
        }

        Object ret = null;
        try {
            if (!result.isContinue()) {
                ret = result.getDefineResult();
            } else {
                ret = zuper.call();
            }
        } catch (Throwable t) {
            try {
                injector.handleMethodException(targetObject, method, allArguments, method.getParameterTypes(), t);
            } catch (Throwable t2) {
                LOG.error("class[{}] handle method[{}] exception failure", obj.getClass(), method.getName(), t2);
                AgentErrorContext.asyncReport(t2, "调用Injector.handleMethodException发生异常:" + obj.getClass() + ":" + t.getMessage());
            }
            throw t;
        } finally {
            try {
                ret = injector.afterMethod(targetObject, method, allArguments, method.getParameterTypes(), ret);
            } catch (Throwable t) {
                LOG.error("class{} after method{} inject failure", obj.getClass(), method.getName(), t);
                AgentErrorContext.asyncReport(t, "调用Injector.afterMethod发生异常:" + obj.getClass());
            }
        }
        return ret;
    }
}

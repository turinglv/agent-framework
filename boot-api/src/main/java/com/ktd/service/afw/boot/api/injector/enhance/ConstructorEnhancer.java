package com.ktd.service.afw.boot.api.injector.enhance;

import com.ktd.service.afw.boot.api.exception.InjectorException;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.boot.api.injector.ConstructorInjector;
import com.ktd.service.afw.boot.api.injector.InjectorInstanceFactory;
import ktd.bb.implementation.bind.annotation.AllArguments;
import ktd.bb.implementation.bind.annotation.RuntimeType;
import ktd.bb.implementation.bind.annotation.This;

public class ConstructorEnhancer {

    /**
     * 日志输出
     */
    private static final Log LOG = LogProvider.getBootLog();

    private ConstructorInjector injector;

    public ConstructorEnhancer(String constructorInjectClassName, ClassLoader targetClassLoader) throws InjectorException {
        try {
            injector = InjectorInstanceFactory.create(constructorInjectClassName, targetClassLoader);
        } catch (Throwable t) {
            throw new InjectorException("Can't create ConstructorInjector.", t);
        }
    }

    @RuntimeType
    public void enhance(@This Object obj, @AllArguments Object[] allArguments) throws Throwable {
        EnhancedMethodResult result = null;
        try {
            if (injector == null || !injector.onOffState()) {
                return;
            }
            EnhancedInstance targetObject = (EnhancedInstance) obj;
            result = injector.onConstruct(targetObject, allArguments);
        } catch (Throwable t) {
            LOG.error("ConstructorEnhancer failure.", t);
        }
        if (result != null && result.getDefineThrowable() != null) {
            throw result.getDefineThrowable();
        }
    }
}

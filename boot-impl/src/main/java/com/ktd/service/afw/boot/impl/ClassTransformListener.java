package com.ktd.service.afw.boot.impl;

import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import ktd.bb.agent.builder.AgentBuilder;
import ktd.bb.description.type.TypeDescription;
import ktd.bb.dynamic.DynamicType;
import ktd.bb.utility.JavaModule;

public class ClassTransformListener implements AgentBuilder.Listener {

    /**
     * 日志输出
     */
    private final Log LOG = LogProvider.getBootLog();

    @Override
    public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
    }

    @Override
    public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {
        LOG.debug("On Transformation class {}.", typeDescription.getName());
        EnhancedClassStorage.INSTANCE.storage(typeDescription, dynamicType);
    }

    @Override
    public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {
    }

    @Override
    public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
        LOG.error("Enhance class " + typeName + " error.", throwable);
    }

    @Override
    public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
    }
}

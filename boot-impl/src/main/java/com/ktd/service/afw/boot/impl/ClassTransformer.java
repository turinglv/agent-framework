package com.ktd.service.afw.boot.impl;

import com.ktd.service.afw.boot.api.config.Configs;
import com.ktd.service.afw.boot.api.injector.InjectStatus;
import com.ktd.service.afw.boot.api.injector.define.AbstractClassInjectorDefine;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.boot.impl.injector.InjectorFinder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ktd.bb.agent.builder.AgentBuilder;
import ktd.bb.description.type.TypeDescription;
import ktd.bb.dynamic.DynamicType;
import ktd.bb.utility.JavaModule;

public class ClassTransformer implements AgentBuilder.Transformer {

    /**
     * 日志输出
     */
    private final Log LOG = LogProvider.getBootLog();

    private InjectorFinder injectorFinder;

    private Set<String> excludeEnhanceClassSet;

    private Set<String> disableInjectorDefineSet;

    public ClassTransformer(InjectorFinder injectorFinder) {
        this.injectorFinder = injectorFinder;
        this.init();
    }

    public void init() {
        String excludeEnhanceClassesProp = Configs.Enhance.EXCLUDE_ENHANCE_CLASSES.getProp();
        excludeEnhanceClassSet = new HashSet<>();
        disableInjectorDefineSet = new HashSet<>();
        if (excludeEnhanceClassesProp != null && !excludeEnhanceClassesProp.isEmpty()) {
            String[] excludeEnhanceClasses = excludeEnhanceClassesProp.split(",");
            for (String excludeEnhanceClass : excludeEnhanceClasses) {
                excludeEnhanceClassSet.add(excludeEnhanceClass);
            }
        }
        String disableInjectorsProp = Configs.Enhance.DISABLE_INJECTOR_DEFINES.getProp();
        if (disableInjectorsProp != null && !disableInjectorsProp.isEmpty()) {
            String[] disableInjectors = disableInjectorsProp.split(",");
            for (String disableInjector : disableInjectors) {
                disableInjectorDefineSet.add(disableInjector);
            }
        }
    }

    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
        if (excludeEnhanceClassSet.contains(typeDescription.getTypeName())) {
            LOG.info("[INJECTOR] Enhance被禁用，所以不对该类进行注入:{}", typeDescription.getTypeName());
            return builder;
        }
        List<AbstractClassInjectorDefine> pluginDefines = injectorFinder.find(typeDescription);
        if (pluginDefines.size() > 0) {
            DynamicType.Builder<?> newBuilder = builder;
            InjectStatus context = new InjectStatus();
            for (AbstractClassInjectorDefine define : pluginDefines) {
                if (disableInjectorDefineSet.contains(define.getClass().getName())) {
                    LOG.info("[INJECTOR] Define被禁用，所以该Define不对该类进行注入:{}", define.getClass().getName(), typeDescription.getTypeName());
                    continue;
                }
                DynamicType.Builder<?> possibleNewBuilder = define.define(typeDescription.getTypeName(), newBuilder, classLoader, context);
                if (possibleNewBuilder != null) {
                    newBuilder = possibleNewBuilder;
                }
            }
            if (context.isMethodInjected()) {
                LOG.debug("Finish the prepare stage for {}.", typeDescription.getName());
            }

            return newBuilder;
        }

        LOG.debug("Matched class {}, but ignore by finding mechanism.", typeDescription.getTypeName());
        return builder;
    }
}
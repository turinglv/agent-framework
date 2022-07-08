package com.ktd.service.afw.boot.api.injector.point;

import ktd.bb.description.method.MethodDescription;
import ktd.bb.matcher.ElementMatcher;

public class DefaultStaticMethodInjectPoint implements StaticMethodInjectPoint {

    private final ElementMatcher<MethodDescription> enhanceMethod;

    private final String injectorClass;

    private final boolean isOverrideArgs;

    public DefaultStaticMethodInjectPoint(ElementMatcher<MethodDescription> enhanceMethod, String injectorClass) {
        this.enhanceMethod = enhanceMethod;
        this.injectorClass = injectorClass;
        this.isOverrideArgs = false;
    }

    public DefaultStaticMethodInjectPoint(ElementMatcher<MethodDescription> enhanceMethod, String injectorClass, boolean isOverrideArgs) {
        this.enhanceMethod = enhanceMethod;
        this.injectorClass = injectorClass;
        this.isOverrideArgs = isOverrideArgs;
    }

    @Override
    public ElementMatcher<MethodDescription> getMethodsMatcher() {
        return enhanceMethod;
    }

    @Override
    public String getMethodsInjectorClass() {
        return injectorClass;
    }

    @Override
    public boolean isOverrideArgs() {
        return isOverrideArgs;
    }
}

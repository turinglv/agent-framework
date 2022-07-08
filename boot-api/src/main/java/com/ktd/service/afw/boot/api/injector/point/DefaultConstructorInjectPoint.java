package com.ktd.service.afw.boot.api.injector.point;

import ktd.bb.description.method.MethodDescription;
import ktd.bb.matcher.ElementMatcher;

/**
 * 默认实现的构造函数 InjectorPoint
 */
public class DefaultConstructorInjectPoint implements ConstructorInjectPoint {

    private final ElementMatcher<MethodDescription> enhanceConstructor;

    private final String injectorClass;

    public DefaultConstructorInjectPoint(ElementMatcher<MethodDescription> enhanceConstructor, String injectorClass) {
        this.enhanceConstructor = enhanceConstructor;
        this.injectorClass = injectorClass;
    }

    @Override
    public ElementMatcher<MethodDescription> getConstructorMatcher() {
        return enhanceConstructor;
    }

    @Override
    public String getConstructorInjectorClass() {
        return injectorClass;
    }
}

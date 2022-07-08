package com.ktd.service.afw.boot.api.injector.point;

import ktd.bb.description.method.MethodDescription;
import ktd.bb.matcher.ElementMatcher;

public interface StaticMethodInjectPoint {

    ElementMatcher<MethodDescription> getMethodsMatcher();

    String getMethodsInjectorClass();

    boolean isOverrideArgs();
}

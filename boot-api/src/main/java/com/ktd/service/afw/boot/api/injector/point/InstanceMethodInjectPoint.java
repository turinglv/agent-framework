package com.ktd.service.afw.boot.api.injector.point;

import ktd.bb.description.method.MethodDescription;
import ktd.bb.matcher.ElementMatcher;

public interface InstanceMethodInjectPoint {
    /**
     * class instance methods matcher.
     *
     * @return methods matcher
     */
    ElementMatcher<MethodDescription> getMethodsMatcher();

    /**
     * @return represents a class name, the class instance must instanceof InstanceMethodAroundInjector.
     */
    String getMethodsInjectorClass();

    boolean isOverrideArgs();
}

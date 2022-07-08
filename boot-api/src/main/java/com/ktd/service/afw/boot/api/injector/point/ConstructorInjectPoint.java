package com.ktd.service.afw.boot.api.injector.point;


import ktd.bb.description.method.MethodDescription;
import ktd.bb.matcher.ElementMatcher;

public interface ConstructorInjectPoint {
    /**
     * Constructor matcher
     *
     * @return matcher instance.
     */
    ElementMatcher<MethodDescription> getConstructorMatcher();

    /**
     * @return represents a class name, the class instance must be a instance define {@link
     */
    String getConstructorInjectorClass();
}

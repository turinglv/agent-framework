package com.ktd.service.afw.boot.api.injector.match;

import ktd.bb.description.type.TypeDescription;
import ktd.bb.matcher.ElementMatcher;

public interface IndirectMatch extends ClassMatch {

    ElementMatcher.Junction buildJunction();

    boolean isMatch(TypeDescription typeDescription);
}

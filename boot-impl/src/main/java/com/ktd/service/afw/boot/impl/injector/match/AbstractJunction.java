package com.ktd.service.afw.boot.impl.injector.match;

import ktd.bb.matcher.ElementMatcher;

public abstract class AbstractJunction<V> implements ElementMatcher.Junction<V> {

    @Override
    public <U extends V> Junction<U> and(ElementMatcher<? super U> other) {
        return new Conjunction<>(this, other);
    }

    @Override
    public <U extends V> Junction<U> or(ElementMatcher<? super U> other) {
        return new Disjunction<>(this, other);
    }
}

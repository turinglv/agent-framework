package com.ktd.service.afw.core.anno.source;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 不可以抛出异常
 */

@Retention(SOURCE)
@Target({TYPE, METHOD})
public @interface CannotThrow {

}

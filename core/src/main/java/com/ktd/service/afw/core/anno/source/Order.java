package com.ktd.service.afw.core.anno.source;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 被次注解注释的属性或者方法，在不熟悉业务逻辑的情况下，不要调整顺序。
 */

@Retention(RUNTIME)
@Target({TYPE, METHOD, CONSTRUCTOR, FIELD})
public @interface Order {

  int value() default 0;
}

package com.ktd.service.afw.core.anno.source;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 之后需要增加的功能
 */
@Retention(SOURCE)
@Target({TYPE, METHOD, CONSTRUCTOR, FIELD, LOCAL_VARIABLE})
public @interface Todo {

  String value() default "之后增加的功能，不是当期需要完成的。";
}

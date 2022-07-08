package com.ktd.service.afw.core.anno.source;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 被该注解标注的类，必须是线程安全的。
 */

@Retention(SOURCE)
@Inherited
@Target({TYPE, METHOD})
public @interface ThreadSafe {

  String value() default "";
}

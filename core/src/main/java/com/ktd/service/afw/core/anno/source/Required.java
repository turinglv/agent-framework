package com.ktd.service.afw.core.anno.source;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 必要的，在传递参数的时候使用。
 */
@Retention(SOURCE)
@Target({TYPE, METHOD, CONSTRUCTOR, FIELD})
public @interface Required {

  /**
   * 描述信息
   */
  String value() default "";
}

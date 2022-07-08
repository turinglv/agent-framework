package com.ktd.service.afw.core.anno.source;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 手动注入的属性
 */
@Retention(SOURCE)
@Target({FIELD})
public @interface FillByHand {

  /**
   * 描述信息
   */
  String value() default "";
}

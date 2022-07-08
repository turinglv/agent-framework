package com.ktd.service.afw.core.anno.source;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 需要明确注意的地方
 */
@Retention(SOURCE)
@Target({TYPE, METHOD, CONSTRUCTOR, FIELD, PARAMETER})
public @interface Important {

  String value();
}

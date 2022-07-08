package com.ktd.service.afw.core.anno.source;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 单例
 */

@Retention(SOURCE)
@Target({TYPE})
public @interface Singleton {

}

package com.ktd.service.afw.boot.impl.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
    /**
     * 默认的实现Class
     */
    Class<?> value();

    String idr();

    /**
     * 取值大的使用
     */
    int priority() default 0;

}

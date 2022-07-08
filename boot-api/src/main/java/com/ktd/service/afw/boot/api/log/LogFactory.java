package com.ktd.service.afw.boot.api.log;

public interface LogFactory {

  Log getLog(String name);

  Log getLog(Class<?> clazz);
}

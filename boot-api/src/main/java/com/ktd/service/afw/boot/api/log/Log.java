package com.ktd.service.afw.boot.api.log;

/**
 * Log接口
 */
public interface Log {

  void debug(String message);

  void debug(String template, Object object);

  void debug(String template, Object... objects);

  void debug(String message, Throwable throwable);

  void info(String message);

  void info(String template, Object object);

  void info(String template, Object... objects);

  void info(String message, Throwable throwable);

  void warn(String message);

  void warn(String template, Object object);

  void warn(String template, Object... objects);

  void warn(String message, Throwable throwable);

  void error(String message);

  void error(String template, Object object);

  void error(String template, Object... objects);

  void error(String message, Throwable throwable);

}

package com.ktd.service.afw.boot.api.log;

public class NoopLogFactory implements LogFactory {

  private static final Log NOOP_LOG = new NoopLog();

  @Override
  public Log getLog(String name) {
    return NOOP_LOG;
  }

  @Override
  public Log getLog(Class<?> clazz) {
    return NOOP_LOG;
  }
}

package com.ktd.service.afw.boot.api.log;

/**
 * 日志提供器
 */
public class LogProvider {

  private static final String AGENT_FW = "AGENT-FRAMEWORK";

  private volatile static LogFactory INSTANCE = new NoopLogFactory();

  public static Log getBootLog() {
    return INSTANCE.getLog(AGENT_FW);
  }

}

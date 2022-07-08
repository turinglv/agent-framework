package com.ktd.service.afw.agent;

import com.alibaba.ttl.threadpool.agent.TtlAgent;
import com.ktd.service.afw.boot.api.util.AgentLogUtil;
import java.lang.instrument.Instrumentation;

/**
 * Ttl的启动类
 */
public final class TtlAgentBootstrap {

  private static final String TTL_AGENT_NAME = "com.alibaba.ttl.threadpool.agent.TtlAgent";

  private static volatile boolean ttlStarted = false;

  /**
   * 私有构造函数
   */
  private TtlAgentBootstrap() {
    super();
  }

  /**
   * 是否有TtlAgent
   *
   * @return true 有，false 无
   */
  public static boolean hasTtl() {
    try {
      Class.forName(TTL_AGENT_NAME);
      return true;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }

  /**
   * 启动TTL
   *
   * @param argument        系统参数
   * @param instrumentation Instrumentation
   */
  public static synchronized void bootTtl(String argument, Instrumentation instrumentation) {
    if (ttlStarted) {
      AgentLogUtil.error(" [AFW] TTL 已经启动，请勿重复启动。");
      return;
    }
    try {
      TtlAgent.premain(argument, instrumentation);
      ttlStarted = true;
    } catch (Exception e) {
      AgentLogUtil.error(" [AFW] 启动TTL 发生故障：", e);
    }
  }

  public static boolean isTtlStarted() {
    return ttlStarted;
  }
}

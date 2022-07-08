package com.ktd.service.afw.boot.api.log;

import com.ktd.service.afw.boot.api.util.AgentLogUtil;
import com.ktd.service.afw.boot.api.thread.AgentThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 启动时的异常存储器
 */
public final class AgentErrorContext {

  private volatile static RemoteErrorSender remoteErrorSender;

  private static final int THREAD_SIZE = 2;

  private static final int KEEP_ALIVE_SENCONDS = 60;

  private static final AgentThreadPoolExecutor EXECUTOR = new AgentThreadPoolExecutor(THREAD_SIZE,
      THREAD_SIZE, KEEP_ALIVE_SENCONDS, TimeUnit.SECONDS, new SynchronousQueue<>(),
      new ThreadPoolExecutor.DiscardPolicy());

  /**
   * 私有构造函数
   */
  private AgentErrorContext() {
    super();
  }

  public static void asyncReport(Throwable error, String message) {
    if (remoteErrorSender == null) {
      AgentLogUtil.error("SENDER为空：", error);
      return;
    }
    if (EXECUTOR.getActiveCountRaw() >= THREAD_SIZE) {
      AgentLogUtil.error("SENDER繁忙，不发送该异常：", error);
      return;
    }
    EXECUTOR.submit(() -> remoteErrorSender.send(error, message));

  }

  public static void syncReport(Throwable error, String message) {
    if (remoteErrorSender == null || error == null) {
      AgentLogUtil.error("SENDER或者Error为空：", error);
      return;
    }
    remoteErrorSender.send(error, message);
  }

  public static void setRemoteErrorSender(RemoteErrorSender remoteErrorSender) {
    AgentErrorContext.remoteErrorSender = remoteErrorSender;
  }
}

package com.ktd.service.afw.boot.impl.initial;

import com.ktd.service.afw.boot.api.initial.ContextInitializer;
import com.ktd.service.afw.boot.api.loader.service.ServiceLoadResult;
import com.ktd.service.afw.boot.api.loader.service.ServiceLoaderHelper;
import com.ktd.service.afw.boot.api.log.AgentErrorContext;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.boot.impl.AfwAgentBootstrap;
import java.util.List;

public class InitializerContext {

  /**
   * 日志输出
   */
  private static final Log LOG = LogProvider.getBootLog();

  public static void init() {
    List<ServiceLoadResult<ContextInitializer>> contextInitializers = ServiceLoaderHelper.loadServices(
        AfwAgentBootstrap.getAgentClassLoader(), ContextInitializer.class, true, true);
    for (ServiceLoadResult<ContextInitializer> contextInitializer : contextInitializers) {
      if (!contextInitializer.getSuccess()) {
        LOG.error("加载ContextInitializer出现错误:" + contextInitializer.getClassName(),
            contextInitializer.getThrowable());
        AgentErrorContext.asyncReport(contextInitializer.getThrowable(),
            "加载ContextInitializer出现错误:" + contextInitializer.getClassName());
        continue;
      }
      LOG.info("[INITIAL.prepare] " + contextInitializer.getPrefix() + "\t=\t"
          + contextInitializer.getClassName());
      contextInitializer.getInstance().prepare();
    }
    for (ServiceLoadResult<ContextInitializer> contextInitializer : contextInitializers) {
      if (!contextInitializer.getSuccess()) {
        continue;
      }
      LOG.info("[INITIAL.boot] " + contextInitializer.getPrefix() + "\t=\t"
          + contextInitializer.getClassName());
      contextInitializer.getInstance().boot();
    }

  }

  /**
   * 私有构造函数
   */
  private InitializerContext() {
    super();
  }
}

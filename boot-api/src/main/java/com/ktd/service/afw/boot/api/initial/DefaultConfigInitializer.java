package com.ktd.service.afw.boot.api.initial;

import com.ktd.service.afw.boot.api.util.AgentLogUtil;
import com.ktd.service.afw.boot.api.config.filler.EnvDefaultConfigFiller;
import com.ktd.service.afw.boot.api.loader.service.ServiceLoadResult;
import com.ktd.service.afw.boot.api.loader.service.ServiceLoaderHelper;
import com.ktd.service.afw.core.exception.AgentException;
import com.ktd.service.afw.core.model.Environment;
import com.ktd.service.afw.core.model.InstanceInfo;

/**
 * DefaultConfigInitializer
 */
public class DefaultConfigInitializer {

  public static void initial() {

    Environment environment = InstanceInfo.INSTANCE.getEnvironment();
    AgentLogUtil.log(" [CONFIG] 检测环境为:" + environment);
    ServiceLoadResult<EnvDefaultConfigFiller> loadResult = ServiceLoaderHelper.loadService(
        environment.name(), Thread.currentThread().getContextClassLoader(),
        EnvDefaultConfigFiller.class, true);
    if (loadResult == null) {
      AgentLogUtil.error(" [CONFIG] 未发现默认属性填充器。");
      throw new AgentException(" [CONFIG] 根据环境配置" + environment + "未发现默认属性填充器。");
    }
    EnvDefaultConfigFiller configFiller = loadResult.getInstance();
    AgentLogUtil.log(" [CONFIG] 发现默认属性填充器：" + configFiller.getClass());
    configFiller.init();
    configFiller.fill();
  }

  /**
   * 私有构造函数
   */
  private DefaultConfigInitializer() {
    super();
  }

}

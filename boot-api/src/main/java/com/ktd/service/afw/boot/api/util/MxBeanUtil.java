package com.ktd.service.afw.boot.api.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * MxBean相关的工具类
 */
public final class MxBeanUtil {

  /**
   * 私有构造函数
   */
  private MxBeanUtil() {
    super();
  }

  public static RuntimeMXBean getRuntimeMXBean() {
    return ManagementFactory.getRuntimeMXBean();
  }
}

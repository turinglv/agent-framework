package com.ktd.service.afw.boot.api.util;

import java.lang.management.RuntimeMXBean;

/**
 * 运行时异常
 */
public final class RuntimeMxUtil {

  private static final int UNKNOWN_PID = -1;

  /**
   * 私有构造函数
   */
  private RuntimeMxUtil() {
  }

  /**
   * 获取启动时间
   */
  public static long getStartTime() {
    return MxBeanUtil.getRuntimeMXBean().getStartTime();
  }

  /**
   * 获取启动的PID
   */
  public static int getPid() {
    RuntimeMXBean runtimeMXBean = MxBeanUtil.getRuntimeMXBean();
    try {
      return Integer.parseInt(runtimeMXBean.getName().split("@")[0]);
    } catch (Exception e) {
      return UNKNOWN_PID;
    }
  }
}

package com.ktd.service.afw.boot.api.util;

import java.io.PrintStream;

/**
 * Log输出错误日志
 */
public final class AgentLogUtil {

  /**
   * 私有构造函数
   */
  private AgentLogUtil() {
    super();
  }

  public static void error(String message) {
    PrintStream stdErr = System.out;
    stdErr.println(" AFW AGENT " + message);
  }

  public static void error(String message, Throwable throwable) {
    PrintStream stdErr = System.out;
    stdErr.println("/*****************");
    stdErr.println("* AFW Agent发生错误，请联系AFW团队");
    stdErr.println("*****************/");
    stdErr.print(" AFW " + message);
    if (throwable != null) {
      stdErr.print(throwable.getClass().getName() + ":" + throwable.getMessage());
    }
    stdErr.println();
  }

  public static void log(String message) {
    PrintStream stdOut = System.out;
    stdOut.println(" AFW " + message);
  }

}

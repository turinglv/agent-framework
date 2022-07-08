package com.ktd.service.afw.boot.api.util;

import com.ktd.service.afw.boot.api.config.Configs;
import com.ktd.service.afw.core.exception.AgentAlarmException;
import com.ktd.service.afw.core.sender.ErrorInfoDTO;
import java.util.HashSet;
import java.util.Set;

/**
 * 异常工具类
 */
public final class ExceptionUtil {

  private static final String NO_EXCEPTION_CAUSE = "com.ktd.service.afw.NoException";

  private static final boolean CONTAIN_STACK = "true".equals(
      System.getProperty(Configs.Log.CONTAIN_STACK.getKey()));

  private static final int MAX_STACK = Integer.valueOf(
          System.getProperty(Configs.Log.MAX_STACK.getKey(), Configs.Log.MAX_STACK.getValue()))
      .intValue();

  /**
   * 私有构造函数
   */
  private ExceptionUtil() {
  }

  /**
   * 获取异常的栈信息
   *
   * @param throwable 异常
   * @return 格式化的异常栈信息
   */
  public static String getStack(Throwable throwable) {
    if (!CONTAIN_STACK) {
      return throwable.getClass() + ":" + throwable.getMessage();
    }
    Set<Throwable> dejaVu = new HashSet<>();
    StringBuilder sb = new StringBuilder();
    ExceptionUtil.appendCause(throwable, dejaVu, sb, 0);
    return sb.toString();
  }

  /**
   * 获取异常的栈信息转换的ErrorInfo
   *
   * @param throwable 异常
   * @return 格式化的异常栈信息
   */
  public static ErrorInfoDTO getErrorInfo(Throwable throwable, boolean withStack) {
    ErrorInfoDTO errorInfo = new ErrorInfoDTO();
    if (throwable == null) {
      errorInfo.setCause(NO_EXCEPTION_CAUSE);
      return errorInfo;
    }
    if (throwable instanceof AgentAlarmException) {
      errorInfo.setAlarmCode(((AgentAlarmException) throwable).getCode());
    }
    errorInfo.setCause(throwable.getClass().getName());
    errorInfo.setMessage(throwable.getMessage());
    if (withStack) {
      Set<Throwable> dejaVu = new HashSet<>();
      StringBuilder sb = new StringBuilder();
      ExceptionUtil.appendCause(throwable, dejaVu, sb, 0);
      errorInfo.setStack(sb.toString());
    }
    return errorInfo;
  }

  /**
   * 追加异常信息到输出
   *
   * @param cause  异常
   * @param dejaVu 已经遍历的异常集合，用于防止异常的循环cause
   * @param sb     输出结果字符串
   * @param depth  当前遍历深度
   * @return 当前遍历深度
   */
  private static int appendCause(Throwable cause, Set<Throwable> dejaVu, StringBuilder sb,
      int depth) {
    if (dejaVu.contains(cause)) {
      // 防止循环依赖打印
      sb.append("\t[CIRCULAR REFERENCE:" + cause + "]\n");
      return depth;
    }
    if (depth > MAX_STACK) {
      sb.append("\t[REACH MAX DEPTH]...\n");
      return depth;
    }
    dejaVu.add(cause);
    //这里使用了 Throwable#printStackTrace方法中的逻辑，默认使用Throwable的toString方法打印。
    sb.append(cause);
    sb.append('\n');
    StackTraceElement[] stacks = cause.getStackTrace();
    int stackIndex = 1;
    for (StackTraceElement stack : stacks) {
      //这里使用了 Throwable#printStackTrace方法中的逻辑，默认使用StackTraceElement#toString方法打印。
      sb.append("\tat " + stack);
      sb.append('\n');
      if (++stackIndex > MAX_STACK) {
        sb.append("\t[REACH MAX STACK]...\n");
        break;
      }
    }

    if (cause.getCause() != null) {
      sb.append("Caused by: ");
      return ExceptionUtil.appendCause(cause.getCause(), dejaVu, sb, ++depth);
    }
    return depth;
  }
}

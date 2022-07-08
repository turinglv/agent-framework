package com.ktd.service.afw.core.sender;

import com.ktd.service.afw.core.exception.AgentAlarmCode;
import java.io.Serializable;

/**
 * 错误信息
 */
public class ErrorInfoDTO implements Serializable {

  /**
   * 目前Java存储的异常类信息，比如：java.lang.NullPointerException
   */
  private String cause;

  /**
   * 一段描述信息
   */
  private String message;

  /**
   * 栈信息
   */
  private String stack;

  /**
   * @see AgentAlarmCode
   */
  private int alarmCode;

  /**
   * 公共构造函数
   */
  public ErrorInfoDTO() {
    super();
  }

  public String getCause() {
    return cause;
  }

  public void setCause(String cause) {
    this.cause = cause;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getStack() {
    return stack;
  }

  public void setStack(String stack) {
    this.stack = stack;
  }

  public int getAlarmCode() {
    return alarmCode;
  }

  public void setAlarmCode(int alarmCode) {
    this.alarmCode = alarmCode;
  }

  /**
   * 这里需要返回JSON型的字符串
   */
  @Override
  public String toString() {
    int length = 50;
    length += (cause == null ? 0 : cause.length());
    length += (message == null ? 0 : message.length());
    length += (stack == null ? 0 : stack.length());
    final StringBuilder sb = new StringBuilder(length);
    sb.append("{'cause':'");
    if (cause != null) {
      sb.append(cause.replace('\'', '"'));
    }
    sb.append("',");
    sb.append("'alarmCode':'");
    sb.append(alarmCode);
    sb.append("',");
    sb.append("'message':'");
    if (message != null) {
      sb.append(message.replace('\'', '"'));
    }
    sb.append("',");
    sb.append("'stack':'");
    if (stack != null) {
      sb.append(stack.replace('\'', '"'));
    }
    sb.append("'}");
    return sb.toString();
  }

}

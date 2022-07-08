package com.ktd.service.afw.core.exception;

public class AgentAlarmException extends AgentException {

  private int code;

  /**
   * 公共构造函数
   */
  public AgentAlarmException() {
    super();
  }

  public AgentAlarmException(int code, String message) {
    super(message);
    this.code = code;
  }

  public AgentAlarmException(int code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}

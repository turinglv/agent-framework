package com.ktd.service.afw.core.response;

/**
 * 请求响应码枚举表
 */
public enum BaseResponseCodeEnum {

  /**
   * 请求成功
   */
  SUCCESS(0, "成功"),

  /**
   * 请求失败 包括业务异常和系统异常
   */
  FAIL(1, "失败");

  /**
   * 响应码
   */
  public final int code;

  /**
   * 响应消息
   */
  public final String message;

  BaseResponseCodeEnum(int code, String message) {
    this.code = code;
    this.message = message;
  }
}

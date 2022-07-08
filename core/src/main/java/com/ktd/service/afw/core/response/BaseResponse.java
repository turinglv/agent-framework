package com.ktd.service.afw.core.response;

import java.io.Serializable;
import java.util.Objects;

/**
 * 请求响应数据结构
 */
public class BaseResponse<T> implements Serializable {

  /**
   * 响应码
   */
  private Integer code;

  /**
   * 响应消息
   */
  private String message;

  /**
   * 响应消息体
   */
  private T data;

  /**
   * 异常类
   */
  private String exceptionClazz;

  /**
   * 异常栈信息
   */
  private String exceptionStack;

  /**
   * 构造函数
   */
  public BaseResponse() {
    super();
  }

  public BaseResponse(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public BaseResponse(Integer code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public BaseResponse(Integer code, String message, String exceptionClazz, String exceptionStack) {
    this.code = code;
    this.message = message;
    this.exceptionClazz = exceptionClazz;
    this.exceptionStack = exceptionStack;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getExceptionClazz() {
    return exceptionClazz;
  }

  public void setExceptionClazz(String exceptionClazz) {
    this.exceptionClazz = exceptionClazz;
  }

  public String getExceptionStack() {
    return exceptionStack;
  }

  public void setExceptionStack(String exceptionStack) {
    this.exceptionStack = exceptionStack;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseResponse<?> that = (BaseResponse<?>) o;
    return Objects.equals(code, that.code) &&
        Objects.equals(message, that.message) &&
        Objects.equals(data, that.data) &&
        Objects.equals(exceptionClazz, that.exceptionClazz) &&
        Objects.equals(exceptionStack, that.exceptionStack);
  }

  @Override
  public int hashCode() {

    return Objects.hash(code, message, data, exceptionClazz, exceptionStack);
  }
}

package com.ktd.service.afw.boot.api.util;

import com.ktd.service.afw.core.response.BaseResponse;
import com.ktd.service.afw.core.response.BaseResponseCodeEnum;

/**
 * 响应相关工具类
 */
public final class BaseResponseUtil {

  /**
   * 私有构造函数
   */
  private BaseResponseUtil() {
    super();
  }

  public static <T> BaseResponse<T> success() {
    return new BaseResponse<>(BaseResponseCodeEnum.SUCCESS.code,
        BaseResponseCodeEnum.SUCCESS.message);
  }

  public static <T> BaseResponse<T> success(T data) {
    return new BaseResponse<>(BaseResponseCodeEnum.SUCCESS.code,
        BaseResponseCodeEnum.SUCCESS.message, data);
  }

  public static <T> BaseResponse<T> fail() {
    return new BaseResponse<>(BaseResponseCodeEnum.FAIL.code, BaseResponseCodeEnum.FAIL.message);
  }

  public static <T> BaseResponse<T> fail(Throwable throwable) {
    return new BaseResponse<>(BaseResponseCodeEnum.FAIL.code, BaseResponseCodeEnum.FAIL.message,
        throwable.getClass().getName(), ExceptionUtil.getStack(throwable));
  }

  public static <T> BaseResponse<T> response(int code, String message) {
    return new BaseResponse<>(code, message);
  }

  public static <T> BaseResponse<T> response(int code, String message, T data) {
    return new BaseResponse<>(code, message, data);
  }

  public static <T> BaseResponse<T> response(int code, String message, String exceptionClazz,
      String exceptionStack) {
    return new BaseResponse<>(code, message, exceptionClazz, exceptionStack);
  }
}

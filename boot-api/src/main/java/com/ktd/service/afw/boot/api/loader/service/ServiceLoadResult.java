package com.ktd.service.afw.boot.api.loader.service;

public class ServiceLoadResult<T> {

  private String prefix;

  private String className;

  private Class<T> clazz;

  private T instance;

  private Boolean success;

  private Throwable throwable;

  /**
   * 公共构造函数
   */
  public ServiceLoadResult() {
    super();
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public Class<T> getClazz() {
    return clazz;
  }

  public void setClazz(Class<T> clazz) {
    this.clazz = clazz;
  }

  public T getInstance() {
    return instance;
  }

  public void setInstance(T instance) {
    this.instance = instance;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public Throwable getThrowable() {
    return throwable;
  }

  public void setThrowable(Throwable throwable) {
    this.throwable = throwable;
  }

  @Override
  public String toString() {
    return "ServiceLoadResult{" +
        "prefix='" + prefix + '\'' +
        ", className='" + className + '\'' +
        ", success=" + success +
        ", throwable=" + throwable +
        '}';
  }
}

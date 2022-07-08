package com.ktd.service.afw.boot.api.injector.define;

import com.ktd.service.afw.boot.api.exception.InjectorException;
import com.ktd.service.afw.boot.api.injector.InjectStatus;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.boot.api.injector.match.ClassMatch;
import com.ktd.service.afw.boot.api.util.StringUtil;
import ktd.bb.dynamic.DynamicType;

public abstract class AbstractClassInjectorDefine {

  /**
   * 日志输出
   */
  private static final Log logger = LogProvider.getBootLog();

  public DynamicType.Builder<?> define(String toEnhanceClassName, DynamicType.Builder<?> builder,
      ClassLoader targetClassLoader, InjectStatus injectStatus) throws InjectorException {
    String injectorDefineClassName = this.getClass().getName();

    if (StringUtil.isEmpty(toEnhanceClassName)) {
      logger.warn("没有找到需要植入字节码的Injector {}.", toEnhanceClassName);
      return null;
    }

    logger.debug("prepare to enhance class {} by {}.", toEnhanceClassName, injectorDefineClassName);

    DynamicType.Builder<?> newClassBuilder = this.enhance(toEnhanceClassName, builder,
        targetClassLoader, injectStatus);

    injectStatus.injectMethodCompleted();
    logger.debug("enhance class {} by {} completely.", toEnhanceClassName, injectorDefineClassName);

    return newClassBuilder;
  }

  protected abstract DynamicType.Builder<?> enhance(String enhanceOriginClassName,
      DynamicType.Builder<?> newClassBuilder, ClassLoader injectorClassLoader, InjectStatus context)
      throws InjectorException;

  public abstract ClassMatch enhanceClass();

}

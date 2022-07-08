package com.ktd.service.afw.inject.cat;

import com.ktd.service.afw.boot.api.core.AbstractBrick;
import com.ktd.service.afw.boot.api.injector.StaticMethodAroundInjector;
import com.ktd.service.afw.boot.api.injector.enhance.EnhancedMethodResult;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.site.lookup.util.StringUtils;
import java.lang.reflect.Method;
import java.util.Objects;

public class CatHomeInjector extends AbstractBrick implements StaticMethodAroundInjector {

  /**
   * 日志输出
   */
  private static final Log LOG = LogProvider.getBootLog();

  @Override
  public void beforeMethod(Class clazz, Method method, Object[] arguments, Class<?>[] argumentTypes,
      EnhancedMethodResult result) {
    // Do Nothing
  }

  @Override
  public Object afterMethod(Class clazz, Method method, Object[] arguments,
      Class<?>[] argumentTypes, Object ret) {

    LOG.info("original cat home {}", ret);

    String catHome = System.getProperty("CAT_HOME");

    if (!isBlank(catHome)) {

      LOG.info("modify cat home {}", catHome);
      return catHome;
    }

    return ret;
  }

  @Override
  public void handleMethodException(Class clazz, Method method, Object[] arguments,
      Class<?>[] argumentTypes, Throwable t) {
    // 这里不能删除，因为不在Trace内，所以要覆盖默认方法。
  }

  @Override
  public int getId() {
    return CatTypeMeta.CAT_INJECTOR.getId();
  }

  private boolean isBlank(final CharSequence cs) {
    int strLen;
    if (cs == null || (strLen = cs.length()) == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if (Character.isWhitespace(cs.charAt(i)) == false) {
        return false;
      }
    }
    return true;
  }
}

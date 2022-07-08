package com.ktd.service.afw.boot.api.util;

import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.core.exception.AgentException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * final属性的工具类
 */
public final class FinalFieldUtil {

  /**
   * 日志输出
   */
  private static final Log LOG = LogProvider.getBootLog();

  /**
   * 修改属性的field
   */
  private static final Field MODIFIERS_FIELD;

  static {
    Field modifiersField;
    try {
      modifiersField = Field.class.getDeclaredField("modifiers");
      modifiersField.setAccessible(true);
    } catch (NoSuchFieldException e) {
      LOG.error("FinalFieldUtil初始化发生异常。", e);
      modifiersField = null;
    }
    MODIFIERS_FIELD = modifiersField;
  }

  /**
   * 私有构造函数
   */
  private FinalFieldUtil() {
    super();
  }

  public static void setValue(Class<?> clazz, String fieldName, Object value) {
    try {

      LOG.info("[INSTANCE.Set] " + clazz.getName());
      Field field = clazz.getDeclaredField(fieldName);
      field.setAccessible(true);
      MODIFIERS_FIELD.setInt(field, field.getModifiers() & ~Modifier.FINAL);
      field.set(null, value);
    } catch (Exception e) {
      e.printStackTrace();
      LOG.error("设置{}#{}为{}发生异常", e);
      throw new AgentException("设置" + clazz + "#" + fieldName + "为" + value + "发生异常。", e);
    }
  }

}

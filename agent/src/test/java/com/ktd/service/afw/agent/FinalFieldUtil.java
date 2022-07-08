package com.ktd.service.afw.agent;

import com.ktd.service.afw.core.exception.AgentException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * final属性的工具类
 *
 */
public final class FinalFieldUtil {

    /**
     * 日志输出
     */
    private static final Logger LOG = LoggerFactory.getLogger(FinalFieldUtil.class);

    /**
     * 私有构造函数
     */
    private FinalFieldUtil() {
        super();
    }

    public static void setValue(Class<?> clazz, String fieldName, Object value) {
        try {
            Field modifiersField;
            modifiersField = Field.class.getDeclaredField("modifiers");
            Field field = clazz.getDeclaredField(fieldName);
            modifiersField.setAccessible(true);
            field.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, value);
        } catch (Exception e) {
            LOG.error("设置{}#{}为{}发生异常", e);
            throw new AgentException("设置" + clazz + "#" + fieldName + "为" + value + "发生异常。", e);
        }
    }

}

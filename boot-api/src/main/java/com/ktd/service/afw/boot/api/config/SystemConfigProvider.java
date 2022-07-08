package com.ktd.service.afw.boot.api.config;

import com.ktd.service.afw.core.kv.KvPair;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统参数工具类
 */
public class SystemConfigProvider implements ConfigProvider {

  protected Map<String, String> cache;

  /**
   * 私有构造函数
   */
  public SystemConfigProvider() {
    super();
    cache = new ConcurrentHashMap<>();
  }

  /**
   * System.prop优先级最高 System.env次之
   *
   * @param key 属性的key
   * @return 获取到的属性信息
   */
  @Override
  public String fromAll(String key) {
    String prop = cache.get(key);
    if (prop != null) {
      return prop;
    }
    prop = System.getProperty(key);
    if (prop != null) {
      return prop;
    }
    return System.getenv(key);
  }

  /**
   * System.prop优先级最高 System.env次之
   *
   * @param key          属性的key
   * @param defaultValue 属性的value
   * @return 获取到的属性信息
   */
  @Override
  public String fromAll(String key, String defaultValue) {
    String prop = cache.get(key);
    if (prop != null) {
      return prop;
    }
    prop = System.getProperty(key);
    if (prop != null) {
      return prop;
    }
    prop = System.getenv(key);
    return prop == null ? defaultValue : prop;
  }

  /**
   * System.prop优先级最高 System.env次之
   *
   * @param kvPair 需要获取的Kv属性对
   * @return 获取到的属性信息
   */
  @Override
  public String fromAll(KvPair<String, String> kvPair) {
    return fromAll(kvPair.getKey(), kvPair.getValue());
  }

  @Override
  public String cacheProp(String key, String value) {
    if (key != null && value != null) {
      return cache.put(key, value);
    }
    return value;
  }

  @Override
  public String removeCache(String key) {
    return cache.remove(key);
  }

  @Override
  public void clearCache() {
    cache.clear();
  }

  public Map<String, String> getCache() {
    return this.cache;
  }
}

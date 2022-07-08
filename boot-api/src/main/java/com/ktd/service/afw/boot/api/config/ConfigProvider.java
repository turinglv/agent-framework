package com.ktd.service.afw.boot.api.config;

import com.ktd.service.afw.core.kv.KvPair;

/**
 * 配置提供器
 */
public interface ConfigProvider {

  ConfigProvider INSTANCE = new SystemConfigProvider();

  /**
   * System.prop优先级最高 System.env次之
   *
   * @param key 属性的key
   * @return 获取到的属性信息
   */
  String fromAll(String key);

  /**
   * System.prop优先级最高 System.env次之
   *
   * @param key          属性的key
   * @param defaultValue 属性的value
   * @return 获取到的属性信息
   */
  String fromAll(String key, String defaultValue);

  /**
   * System.prop优先级最高 System.env次之
   *
   * @param kvPair 需要获取的Kv属性对
   * @return 获取到的属性信息
   */
  String fromAll(KvPair<String, String> kvPair);

  /**
   * 将Key和Value缓存到系统中，之后优先从缓存中获取该属性值
   *
   * @param key   属性的Key值
   * @param value 属性的value值
   * @return 如果原本cache中有数据，则返回cache中的旧数据
   */
  String cacheProp(String key, String value);

  /**
   * 删除Cache
   *
   * @param key 需要删除cache的key
   * @return key对应的value
   */
  String removeCache(String key);

  /**
   * 清空cache
   */
  void clearCache();
}

package com.ktd.service.afw.boot.api.config;

/**
 * {@link KvConfig} 工厂类
 *
 */
public final class KvConfigFactory {

  /**
   * 私有构造函数
   */
  private KvConfigFactory() {
    super();
  }

  /**
   * 生成KvPair
   *
   * @param key   键
   * @param value 值
   * @return 生成的KvPair
   */
  public static KvConfig of(String key, String value) {
    return new KvConfig(key, value);
  }

  /**
   * 生成KvPair
   *
   * @param key   键
   * @param value 值
   * @return 生成的KvPair
   */
  public static KvmConfig of(String key, String value, String magic) {
    return new KvmConfig(key, value, magic);
  }

}

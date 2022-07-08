package com.ktd.service.afw.core.kv;

/**
 * {@link KvPair} 和 {@link KvmPair} 工厂类
 */
public final class KvFactory {

  /**
   * 私有构造函数
   */
  private KvFactory() {
    super();
  }

  /**
   * 生成KvPair
   *
   * @param key   键
   * @param value 值
   * @param <K>   键类型
   * @param <V>   值类型
   * @return 生成的KvPair
   */
  public static <K, V> KvPair<K, V> of(K key, V value) {
    return new DefaultKvPair<>(key, value);
  }

  public static <K, V, M> KvmPair<K, V, M> of(K key, V value, M magic) {
    return new DefaultKvmPair<>(key, value, magic);
  }

}


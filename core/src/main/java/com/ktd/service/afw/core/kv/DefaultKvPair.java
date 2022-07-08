package com.ktd.service.afw.core.kv;

import java.util.Objects;

/**
 * KvPair对
 */
public class DefaultKvPair<K, V> implements KvPair<K, V> {

  /**
   * 键
   */
  private final K key;

  /**
   * 值
   */
  private final V value;

  /**
   * 全参构造函数
   *
   * @param key   键
   * @param value 值
   */
  public DefaultKvPair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public K getKey() {
    return key;
  }

  @Override
  public V getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DefaultKvPair<?, ?> that = (DefaultKvPair<?, ?>) o;
    return Objects.equals(key, that.key) &&
        Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {

    return Objects.hash(key, value);
  }

  @Override
  public String toString() {
    return "KvPair{" +
        "key=" + key +
        ", value=" + value +
        '}';
  }
}

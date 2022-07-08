package com.ktd.service.afw.core.kv;

import java.util.Objects;

/**
 * KVM三元组
 */
public class DefaultKvmPair<K, V, M> implements KvmPair<K, V, M> {

  /**
   * 键
   */
  private K key;

  /**
   * 值
   */
  private V value;

  /**
   * 神奇
   */
  private M magic;

  /**
   * 构造函数
   *
   * @param key   键
   * @param value 值
   * @param magic 神奇
   */
  public DefaultKvmPair(K key, V value, M magic) {
    this.key = key;
    this.value = value;
    this.magic = magic;
  }

  @Override
  public K getKey() {
    return key;
  }

  public void setKey(K key) {
    this.key = key;
  }

  @Override
  public V getValue() {
    return value;
  }

  public void setValue(V value) {
    this.value = value;
  }

  @Override
  public M getMagic() {
    return magic;
  }

  public void setMagic(M magic) {
    this.magic = magic;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DefaultKvmPair<?, ?, ?> that = (DefaultKvmPair<?, ?, ?>) o;
    return Objects.equals(key, that.key) &&
        Objects.equals(value, that.value) &&
        Objects.equals(magic, that.magic);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value, magic);
  }

  @Override
  public String toString() {
    return "KvmPair{" +
        "key=" + key +
        ", value=" + value +
        ", magic=" + magic +
        '}';
  }
}


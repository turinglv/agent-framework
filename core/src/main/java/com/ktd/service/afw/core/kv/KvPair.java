package com.ktd.service.afw.core.kv;

/**
 * KvPair对
 */
public interface KvPair<K, V> {

  K getKey();

  V getValue();

}

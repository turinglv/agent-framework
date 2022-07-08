package com.ktd.service.afw.core.kv;

/**
 * KVM三元组
 */
public interface KvmPair<K, V, M> extends KvPair<K, V> {

  M getMagic();

}


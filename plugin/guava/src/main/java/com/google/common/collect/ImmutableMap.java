package com.google.common.collect;

import java.util.HashMap;
import java.util.Map;

public class ImmutableMap {

  public static <K, V> Map<K, V> copyOf(Map<? extends K, ? extends V> map) {
    return new HashMap<>(map);
  }

  public static <K, V> Builder<K, V> builder() {
    return new Builder<>();
  }

  public static class Builder<K, V> {

    private Map<K, V> map = new HashMap<>();

    public Builder<K, V> put(K key, V value) {
      map.put(key, value);
      return this;
    }

    public Map<K, V> build() {
      return map;
    }
  }
}

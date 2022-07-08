package com.google.common.collect;

import com.google.common.base.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class Maps {

  public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
    return new LinkedHashMap<>();
  }

  public static <K, V> Map.Entry<K, V> immutableEntry(@Nullable K key, @Nullable V value) {
    return new ImmutableEntry<>(key, value);
  }
}

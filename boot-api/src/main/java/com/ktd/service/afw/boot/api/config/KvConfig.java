package com.ktd.service.afw.boot.api.config;

import com.ktd.service.afw.core.kv.KvPair;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Option的Kv组
 */
public class KvConfig implements KvPair<String, String> {

  private static final String SEPARATOR = ",";

  private final String key;

  private final String value;

  public KvConfig(String key, String value) {
    super();
    this.key = key;
    this.value = value;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public String getValue() {
    return value;
  }

  public String getProp() {
    return ConfigProvider.INSTANCE.fromAll(this);
  }

  public Boolean getBoolean() {
    String prop = ConfigProvider.INSTANCE.fromAll(this);
    if (prop == null) {
      return null;
    }
    return Boolean.valueOf(prop);
  }

  public Integer getInteger() {
    String prop = ConfigProvider.INSTANCE.fromAll(this);
    if (prop == null) {
      return null;
    }
    return Integer.valueOf(prop);
  }

  public Collection<String> getCollection() {
    String prop = ConfigProvider.INSTANCE.fromAll(this);
    if (prop == null || prop.isEmpty()) {
      return Collections.emptyList();
    }
    String[] tokens = prop.split(SEPARATOR);
    Set<String> result = new HashSet<>(tokens.length);
    for (String token : tokens) {
      result.add(token);
    }
    return result;
  }

  @Override
  public String toString() {
    return "KvConfig{" +
        "key='" + key + '\'' +
        ", value='" + value + '\'' +
        '}';
  }
}

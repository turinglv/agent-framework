package com.ktd.service.afw.boot.api.config;

import com.ktd.service.afw.core.kv.KvPair;

/**
 * Option的Kv组
 *
 */
public class KvmConfig implements KvPair<String, String> {

  private final String key;

  private final String value;

  private final String magic;

  public KvmConfig(String key, String value, String magic) {
    super();
    this.key = key;
    this.value = value;
    this.magic = magic;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public String getValue() {
    return value;
  }

  public String getMagic() {
    return magic;
  }

  @Override
  public String toString() {
    return "KvmConfig{" +
        "key='" + key + '\'' +
        ", value='" + value + '\'' +
        ", magic='" + magic + '\'' +
        '}';
  }
}

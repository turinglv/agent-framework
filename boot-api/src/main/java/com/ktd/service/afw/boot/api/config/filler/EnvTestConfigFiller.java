package com.ktd.service.afw.boot.api.config.filler;

import com.ktd.service.afw.boot.api.config.Configs;
import com.ktd.service.afw.boot.api.config.KvConfig;
import com.ktd.service.afw.core.kv.KvPair;
import java.util.ArrayList;
import java.util.List;

public class EnvTestConfigFiller implements EnvDefaultConfigFiller {

  private static final class Instance {

    // TODO
    private static final String ERROR_REPORT_HTTP_URL = "xxxx";
  }

  private final List<KvPair<String, String>> fillKvPairs;

  /**
   * 公共构造函数
   */
  public EnvTestConfigFiller() {
    super();
    fillKvPairs = new ArrayList<>();
  }

  @Override
  public void init() {
    // Instance
    fillKvPairs.add(new KvConfig(Configs.Instance.ERROR_REPORT_HTTP_URL.getKey(),
        Instance.ERROR_REPORT_HTTP_URL));
  }

  @Override
  public List<KvPair<String, String>> getKvPairs() {
    return fillKvPairs;
  }
}

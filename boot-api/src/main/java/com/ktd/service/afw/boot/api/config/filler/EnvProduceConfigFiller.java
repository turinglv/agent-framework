package com.ktd.service.afw.boot.api.config.filler;

import com.ktd.service.afw.boot.api.config.Configs;
import com.ktd.service.afw.boot.api.config.KvConfig;
import com.ktd.service.afw.core.kv.KvPair;
import java.util.ArrayList;
import java.util.List;

public class EnvProduceConfigFiller implements EnvDefaultConfigFiller {

  private static final class Instance {

    // todo
    private static final String ERROR_REPORT_HTTP_URL = "xxxx";

    // [END] 本地文件中的属性 == END == END == END == END == END == END == END == END == END == END == END == END ==

    private Instance() {
      super();
    }
  }

  private final List<KvPair<String, String>> fillKvPairs;

  /**
   * 公共构造函数
   */
  public EnvProduceConfigFiller() {
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

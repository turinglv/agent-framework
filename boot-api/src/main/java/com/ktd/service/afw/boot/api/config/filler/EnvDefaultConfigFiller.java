package com.ktd.service.afw.boot.api.config.filler;


import com.ktd.service.afw.boot.api.config.ConfigProvider;
import com.ktd.service.afw.boot.api.util.AgentLogUtil;
import com.ktd.service.afw.core.kv.KvPair;
import java.util.List;

public interface EnvDefaultConfigFiller {

  default void fill() {
    for (KvPair<String, String> kvPair : this.getKvPairs()) {
      if (ConfigProvider.INSTANCE.fromAll(kvPair.getKey()) == null) {
        AgentLogUtil.log(" [CONFIG] 根据环境填充配置:" + kvPair);
        ConfigProvider.INSTANCE.cacheProp(kvPair.getKey(), kvPair.getValue());
      }
    }
  }

  void init();

  List<KvPair<String, String>> getKvPairs();
}

package com.ktd.service.afw.boot.api.core;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.jar.Manifest;

public class AgentInfo {

  private final String agentPath;

  private final Manifest manifest;

  private final List<URL> bootUrlList;

  private final List<URL> injectorUrlList;

  public AgentInfo(String agentPath, Manifest manifest, List<URL> bootUrlList,
      List<URL> injectorUrlList) {
    this.agentPath = agentPath;
    this.manifest = manifest;
    this.bootUrlList = bootUrlList;
    this.injectorUrlList = injectorUrlList;
  }

  public String getAgentPath() {
    return agentPath;
  }

  public Manifest getManifest() {
    return manifest;
  }

  public URL[] getBootUrls() {
    return bootUrlList.toArray(new URL[bootUrlList.size()]);
  }

  public URL[] getInjectorUrls() {
    return injectorUrlList.toArray(new URL[injectorUrlList.size()]);
  }

  public List<URL> getBootUrlList() {
    return Collections.unmodifiableList(bootUrlList);
  }

  public List<URL> getInjectorUrlList() {
    return Collections.unmodifiableList(injectorUrlList);
  }
}

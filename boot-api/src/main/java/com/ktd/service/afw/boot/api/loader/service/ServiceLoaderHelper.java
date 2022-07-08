package com.ktd.service.afw.boot.api.loader.service;

import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.core.exception.AgentException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class ServiceLoaderHelper {

  private static final String RESOURCE_PREFIX = "META-INF/afw/";

  private static final String SERVICE_SEPARATOR = "=";

  /**
   * 日志输出
   */
  private static final Log LOG = LogProvider.getBootLog();

  /**
   * 私有构造函数
   */
  private ServiceLoaderHelper() {
    super();
  }


  public static <T> ServiceLoadResult<T> loadService(String idr, ClassLoader classLoader,
      Class<T> clazz, boolean newInstance) {
    if (classLoader == null || clazz == null) {
      return null;
    }
    List<ServiceLoadResult<T>> serviceLoadResults = loadServices(classLoader, clazz.getName(),
        false, false);
    ServiceLoadResult result = null;
    for (ServiceLoadResult serviceLoadResult : serviceLoadResults) {
      if (idr.equalsIgnoreCase(serviceLoadResult.getPrefix())) {
        result = serviceLoadResult;
        break;
      }
    }
    if (result == null) {
      return result;
    }
    try {
      result.setClazz(classLoader.loadClass(result.getClassName()));
      if (newInstance) {
        result.setInstance(result.getClazz().newInstance());
      }
    } catch (Throwable e) {
      result.setThrowable(e);
      throw new AgentException("加载Class发生异常:" + result.getClassName(), e);
    }
    return result;
  }


  public static <T> List<ServiceLoadResult<T>> loadServices(ClassLoader classLoader, Class<T> clazz,
      boolean loadClass, boolean newInstance) {
    if (classLoader == null || clazz == null) {
      return Collections.emptyList();
    }
    return loadServices(classLoader, clazz.getName(), loadClass, newInstance);
  }

  public static <T> List<ServiceLoadResult<T>> loadServices(ClassLoader classLoader,
      String className, boolean loadClass, boolean newInstance) {
    List<ServiceLoadResult<T>> results = new ArrayList<>();
    try {
      Enumeration<URL> urlEnumeration = classLoader.getResources(RESOURCE_PREFIX + className);
      while (urlEnumeration.hasMoreElements()) {
        URL url = urlEnumeration.nextElement();
        results.addAll(parseFromUrl(url));
      }
    } catch (IOException e) {
      return Collections.emptyList();
    }
    if (!loadClass && !newInstance) {
      return results;
    }
    for (ServiceLoadResult result : results) {
      try {
        Class<T> clazz = (Class<T>) Class.forName(result.getClassName(), true, classLoader);
        result.setClazz(clazz);
        if (newInstance) {
          result.setInstance(clazz.newInstance());
        }
        result.setSuccess(true);
      } catch (Exception e) {
        LOG.error("加载Result的Class和实例", e);
        result.setThrowable(e);
        result.setSuccess(false);
      }
    }
    return results;
  }

  public static <T> List<ServiceLoadResult<T>> parseFromUrl(URL url) {
    List<ServiceLoadResult<T>> results = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
      for (String line = br.readLine(); line != null; line = br.readLine()) {
        if (line.trim().isEmpty() || line.startsWith("#")) {
          continue;
        }
        ServiceLoadResult result = new ServiceLoadResult();
        String[] tokens = line.split(SERVICE_SEPARATOR);
        result.setPrefix(tokens[0]);
        result.setClassName(tokens[1]);
        results.add(result);
      }
    } catch (IOException e) {
      LOG.error("读取Afw服务发生异常：", e);
    }
    return results;
  }
}

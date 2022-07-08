package com.ktd.service.afw.boot.api.core;

import com.ktd.service.afw.boot.api.loader.archive.Archive;
import com.ktd.service.afw.boot.api.util.AgentLogUtil;
import com.ktd.service.afw.boot.api.util.MxBeanUtil;
import com.ktd.service.afw.boot.api.loader.archive.JarFileArchive;
import com.ktd.service.afw.boot.api.loader.afw.ProtocolHandlerRegister;
import com.ktd.service.afw.core.exception.AgentException;
import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.management.RuntimeMXBean;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * AgentPath相关的工具类
 */
public final class AgentContext {

  /**
   * Window的文件分隔符的正则表达式
   */
  private static final char WINDOWS_FILE_SEPARATOR_REGEX = '\\';
  /**
   * Linux的文件分隔符
   */
  private static final char LINUX_FILE_SEPARATOR = '/';
  /**
   * Jar包之间的分隔符
   */
  private static final String JAR_SEPARATOR = "[;:]";
  /**
   * Agent jar包的正则表达式
   */
  private static final String AGENT_PATTERN_REGEX = "afw\\-agent.*\\.jar";
  /**
   * Agent Jar包的Pattern
   */
  private static final Pattern AGENT_PATTERN = Pattern.compile(AGENT_PATTERN_REGEX);
  /**
   * Agent的前缀
   */
  private static final String AGENT_PREFIX = "-javaagent:";
  /**
   * Agent前缀的长度
   */
  private static final int AGENT_PREFIX_LENGTH = AGENT_PREFIX.length();
  /**
   * bootclasspath的前缀
   */
  private static final String BOOT_PREFIX = "-Xbootclasspath/a:";
  /**
   * bootclasspath前缀的长度
   */
  private static final int BOOT_PREFIX_LENGTH = BOOT_PREFIX.length();

  private static Instrumentation instrumentation;

  /**
   * 私有构造函数
   */
  private AgentContext() {
    super();
  }

  public static volatile AgentInfo agentInfo;

  public static AgentInfo getAgentInfo() {
    return agentInfo;
  }

  public static void init() {
    String agentJarPath = getAgentPath();
    if (agentJarPath == null) {
      return;
    }
    try {
      URI.create("jar:" + agentJarPath);
      ProtocolHandlerRegister.registerUrlProtocolHandler();
      JarFileArchive jarFileArchive = new JarFileArchive(new File(agentJarPath));
      List<Archive> bootArchives = jarFileArchive.getNestedArchives(
          entry -> entry.getName().startsWith("boot/") && entry.getName().endsWith(".jar"));
      List<URL> bootUrls = new ArrayList<>();
      for (Archive bootArchive : bootArchives) {
        bootUrls.add(bootArchive.getUrl());
      }
      List<Archive> injectArchives = jarFileArchive.getNestedArchives(
          entry -> entry.getName().startsWith("inject/") && entry.getName().endsWith(".jar"));
      List<URL> injectorUrls = new ArrayList<>();
      for (Archive injectArchive : injectArchives) {
        injectorUrls.add(injectArchive.getUrl());
      }
      AgentInfo agentInfo = new AgentInfo(agentJarPath, jarFileArchive.getManifest(), bootUrls,
          injectorUrls);
      AgentContext.agentInfo = agentInfo;
    } catch (Exception e) {
      AgentLogUtil.error(" 读取Injector发生异常:" + agentJarPath, e);
      throw new AgentException("读取Injector发生异常。");
    }
  }

  /**
   * 初始化Agent类
   *
   * @return 初始化Agent的完整路径
   */
  protected static String getAgentPath() {

    /**
     * 从boot path中获取Agent的jar包路径
     */
    RuntimeMXBean runtimeMXBean = MxBeanUtil.getRuntimeMXBean();

    /**
     * 从java agent中获取Agent的jar包路径
     */
    List<String> inputArgs = runtimeMXBean.getInputArguments();
    for (String inputArg : inputArgs) {
      String bootJarPath;
      if (inputArg.startsWith(AGENT_PREFIX)) {
        bootJarPath = inputArg.substring(AGENT_PREFIX_LENGTH)
            .replace(WINDOWS_FILE_SEPARATOR_REGEX, LINUX_FILE_SEPARATOR);
      } else if (inputArg.startsWith(BOOT_PREFIX)) {
        bootJarPath = inputArg.substring(BOOT_PREFIX_LENGTH)
            .replace(WINDOWS_FILE_SEPARATOR_REGEX, LINUX_FILE_SEPARATOR);
      } else {
        continue;
      }
      if (isMatchAgentJar(bootJarPath)) {
        return bootJarPath;
      }
    }
    if (!runtimeMXBean.isBootClassPathSupported()) {
      return null;
    }
    String bootClassPath = runtimeMXBean.getBootClassPath();
    if (bootClassPath == null || bootClassPath.isEmpty()) {
      return null;
    }
    String[] bootJarPaths = bootClassPath.split(JAR_SEPARATOR);
    for (String bootJarPath : bootJarPaths) {
      //转换为Linux
      bootJarPath = bootJarPath.replace(WINDOWS_FILE_SEPARATOR_REGEX, LINUX_FILE_SEPARATOR);
      if (isMatchAgentJar(bootJarPath)) {
        return bootJarPath;
      }

    }
    // 没有找到Agent的jar包路径
    return null;
  }

  /**
   * 路径中是否包含了afw的Agent jar包，匹配{@link #AGENT_PATTERN_REGEX}
   *
   * @param fileFullPath afw Agent的完整路径
   * @retur 匹配则返回true，否则返回false
   */
  private static boolean isMatchAgentJar(String fileFullPath) {
    int lastFileIndex = fileFullPath.lastIndexOf(LINUX_FILE_SEPARATOR);
    String jarFileName;
    if (lastFileIndex > 0) {
      jarFileName = fileFullPath.substring(lastFileIndex + 1);
    } else {
      jarFileName = fileFullPath;
    }
    return AGENT_PATTERN.matcher(jarFileName).matches();
  }

  public static void setInstrumentation(Instrumentation instrumentation) {
    AgentContext.instrumentation = instrumentation;
  }

  public static Instrumentation getInstrumentation() {
    return instrumentation;
  }
}

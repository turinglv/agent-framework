package com.ktd.service.afw.agent;

import com.ktd.service.afw.boot.api.config.Configs;
import com.ktd.service.afw.boot.api.core.AgentContext;
import com.ktd.service.afw.boot.api.core.AgentInfo;
import com.ktd.service.afw.boot.api.core.AgentManifestCst;
import com.ktd.service.afw.boot.api.initial.DefaultConfigInitializer;
import com.ktd.service.afw.boot.api.initial.InstanceInfoInitializer;
import com.ktd.service.afw.boot.api.loader.LaunchedURLClassLoader;
import com.ktd.service.afw.boot.api.log.AgentErrorContext;
import com.ktd.service.afw.boot.api.log.HttpRemoteErrorSender;
import com.ktd.service.afw.boot.api.util.AgentLogUtil;
import com.ktd.service.afw.core.exception.AgentException;
import java.io.PrintStream;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Agent启动类
 */
public class AfwAgent {

  /**
   * 是否开启Agent
   */
  public static final String AGENT_OPEN = "agent.open";

  /**
   * 是否开启TTL
   */
  public static final String AFW_TTL_OPEN = "afw.ttl.open";

  /**
   * LOGO
   */
  public static final String LOGO = ""
      + "      ___         ___      ___         ___         ___         ___         ___               \n"
      + "     /  /\\       /  /\\    /__/\\       /  /\\       /  /\\       /  /\\       /__/\\        ___   \n"
      + "    /  /::\\     /  /:/_  _\\_ \\:\\     /  /::\\     /  /:/_     /  /:/_      \\  \\:\\      /  /\\  \n"
      + "   /  /:/\\:\\   /  /:/ /\\/__/\\ \\:\\   /  /:/\\:\\   /  /:/ /\\   /  /:/ /\\      \\  \\:\\    /  /:/  \n"
      + "  /  /:/~/::\\ /  /:/ /:_\\_ \\:\\ \\:\\ /  /:/~/::\\ /  /:/_/::\\ /  /:/ /:/_ _____\\__\\:\\  /  /:/   \n"
      + " /__/:/ /:/\\:/__/:/ /:/__/\\ \\:\\ \\:/__/:/ /:/\\:/__/:/__\\/\\:/__/:/ /:/ //__/::::::::\\/  /::\\   \n"
      + " \\  \\:\\/:/__\\\\  \\:\\/:/\\  \\:\\ \\:\\/:\\  \\:\\/:/__\\\\  \\:\\ /~~/:\\  \\:\\/:/ /:\\  \\:\\~~\\~~\\/__/:/\\:\\  \n"
      + "  \\  \\::/     \\  \\::/  \\  \\:\\ \\::/ \\  \\::/     \\  \\:\\  /:/ \\  \\::/ /:/ \\  \\:\\  ~~~\\__\\/  \\:\\ \n"
      + "   \\  \\:\\      \\  \\:\\   \\  \\:\\/:/   \\  \\:\\      \\  \\:\\/:/   \\  \\:\\/:/   \\  \\:\\         \\  \\:\\\n"
      + "    \\  \\:\\      \\  \\:\\   \\  \\::/     \\  \\:\\      \\  \\::/     \\  \\::/     \\  \\:\\         \\__\\/\n"
      + "     \\__\\/       \\__\\/    \\__\\/       \\__\\/       \\__\\/       \\__\\/       \\__\\/              \n";

  /**
   * AFW Agent的启动类
   */
  private static final String AFW_BOOT_CLASS = "com.ktd.service.afw.boot.impl.AfwAgentBootstrap";

  /**
   * 防止Agent重复启动。
   */
  protected static final AtomicBoolean AGENT_START_FLAG = new AtomicBoolean(false);

  private static volatile boolean started = false;

  public static void premain(String argument, Instrumentation instrumentation) {
    PrintStream stdOut = System.out;
    if (!AGENT_START_FLAG.compareAndSet(false, true)) {
      stdOut.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
      stdOut.println(" Agent 已经启动。。。您可能指定了两次agent参数。");
      return;
    }

    stdOut.print(Color.CYAN);
    stdOut.println(
        "================================================================================================");
    stdOut.print(Color.GREEN);
    stdOut.println();
    stdOut.println(LOGO);
    stdOut.print(Color.CYAN);

    AgentContext.init();
    AgentInfo agentInfo = AgentContext.getAgentInfo();
    if (agentInfo != null) {
      stdOut.println(
          " AFW AGENT Version     : " + AfwAgent.class.getPackage().getImplementationVersion());
      stdOut.println(
          " AFW AGENT Build Date  : " + agentInfo.getManifest().getMainAttributes().getValue(
              AgentManifestCst.BUILD_DATE));
      stdOut.println(" AFW AGENT Path        : " + agentInfo.getAgentPath());
    }

    boolean ttlOpenFlag = !Boolean.FALSE.toString()
        .equalsIgnoreCase(System.getProperty(AFW_TTL_OPEN));
    boolean agentOpenFlag = !Boolean.FALSE.toString()
        .equalsIgnoreCase(System.getProperty(AGENT_OPEN));
    stdOut.println(" AGENT Open    : " + agentOpenFlag);
    stdOut.println(" AGENT Open.TTL    : " + ttlOpenFlag);

    InitSystemProperty.init();

    // 启动ttl
    startTtl(argument, instrumentation, ttlOpenFlag);

    // init Instance信息
    InstanceInfoInitializer.initial();

    // 启动Agent
    startAgent(argument, instrumentation, agentInfo, agentOpenFlag);

    stdOut.println(Color.CYAN);
    stdOut.println(
        "================================================================================================");
    stdOut.println(Color.RESET);
  }

  public static boolean isStarted() {
    return started;
  }

  private static class Color {

    private static final byte[] CYAN_BYTES = {27, 91, 51, 54, 109, 10};
    public static final String CYAN = new String(CYAN_BYTES);
    private static final byte[] GREEN_BYTES = {27, 91, 51, 50, 109, 10};
    public static final String GREEN = new String(GREEN_BYTES);
    private static final byte[] RESET_BYTES = {27, 91, 109, 10};
    public static final String RESET = new String(RESET_BYTES);

    /**
     * 私有构造函数
     */
    private Color() {
      super();
    }
  }

  private static void startTtl(String argument, Instrumentation instrumentation,
      boolean ttlOpenFlag) {
    PrintStream stdOut = System.out;
    try {
      if (!ttlOpenFlag) {
        stdOut.println(" AFW [TTL] TTL开关未开启，不启动TTL！！！");
        stdOut.println(" AFW [TTL] TTL开关未开启，不启动TTL！！！");
      } else if (TtlAgentBootstrap.hasTtl()) {
        stdOut.println(Color.CYAN);
        stdOut.println(" AFW [TTL] 发现-->启动中....");
        TtlAgentBootstrap.bootTtl(argument, instrumentation);
        stdOut.println(" AFW [TTL] 启动完毕.");
      } else {
        stdOut.println(" AFW [TTL] 未发现！！！！请确认！！！");
        stdOut.println(" AFW [TTL] 未发现！！！！请确认！！！");
        stdOut.println(" AFW [TTL] 未发现！！！！请确认！！！");
      }
    } catch (Exception e) {
      AgentLogUtil.error(" AFW [TTL] TTL启动发生异常！！！", e);
    }
  }

  private static void startAgent(String argument, Instrumentation instrumentation,
      AgentInfo agentInfo, boolean agentOpenFlag) {
    PrintStream stdOut = System.out;
    DefaultConfigInitializer.initial();
    HttpRemoteErrorSender errorSender = new HttpRemoteErrorSender();
    errorSender.setReportUrl(Configs.Instance.ERROR_REPORT_HTTP_URL.getProp());
    AgentErrorContext.setRemoteErrorSender(errorSender);
    if (agentInfo == null) {
      stdOut.println(" AGENT 未正常找到格式正确的Agent包!!!!!!");
      stdOut.println(" AGENT 未正常找到格式正确的Agent包!!!!!!");
      stdOut.println(" AGENT 未正常找到格式正确的Agent包!!!!!!");
      return;
    }
    stdOut.print(Color.CYAN);
    if (agentOpenFlag) {
      stdOut.println(" AFW [AGENT] 启动中....");
      ClassLoader agentClassLoader = null;
      ClassLoader contextClassLoader = null;
      try {
        contextClassLoader = Thread.currentThread().getContextClassLoader();
        stdOut.println(" AFW [AGENT]  上下文加载器：" + contextClassLoader.toString());
        agentClassLoader = new LaunchedURLClassLoader(agentInfo.getBootUrls(),
            ClassLoader.getSystemClassLoader().getParent());
        Thread.currentThread().setContextClassLoader(agentClassLoader);
        Class<?> bootClass = Class.forName(AFW_BOOT_CLASS, true, agentClassLoader);
        Method method = bootClass.getMethod("premain", String.class, Instrumentation.class);
        method.invoke(null, argument, instrumentation);
        stdOut.println(" AFW [AGENT] 启动完毕.");
        started = true;
      } catch (Exception e) {
        stdOut.println(" AFW [AGENT] 启动发生异常，未启动!!!!.");
        AgentLogUtil.error(" 启动Agent发生错误", e);
        AgentException exception = new AgentException("启动Agent发生错误", e);
        AgentErrorContext.syncReport(exception, "启动Agent发生错误");
        throw exception;
      } finally {
        if (agentClassLoader != null && contextClassLoader != null) {
          if (Thread.currentThread().getContextClassLoader() != agentClassLoader) {
            stdOut.println(
                "AFW [AGENT] 上下文CL发生变化，需要找出原因。原：" + agentClassLoader.toString() + "\t 目前:"
                    + Thread.currentThread().getContextClassLoader());
          }
          // 不管发生了什么，都要还原
          Thread.currentThread().setContextClassLoader(contextClassLoader);
        }
      }
    } else {
      stdOut.println(" AFW [AGENT] 开关关闭，不启动！！！");
      stdOut.println(" AFW [AGENT] 开关关闭，不启动！！！");
    }
  }

}

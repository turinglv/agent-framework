package com.ktd.service.afw.boot.api.initial;

import com.ktd.service.afw.boot.api.config.ConfigProvider;
import com.ktd.service.afw.boot.api.config.Configs;
import com.ktd.service.afw.boot.api.config.KvmConfig;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.boot.api.util.AgentLogUtil;
import com.ktd.service.afw.boot.api.util.IpUtil;
import com.ktd.service.afw.boot.api.util.RuntimeMxUtil;
import com.ktd.service.afw.boot.api.core.AgentCst;
import com.ktd.service.afw.boot.api.core.DefaultInstanceInfo;
import com.ktd.service.afw.boot.api.util.FinalFieldUtil;
import com.ktd.service.afw.core.exception.AgentException;
import com.ktd.service.afw.core.model.Environment;
import com.ktd.service.afw.core.model.InstanceInfo;
import com.ktd.service.afw.core.model.InstanceStatus;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Instance信息初始化器
 */
public final class InstanceInfoInitializer {

  /**
   * 日志输出
   */
  private static final Log LOG = LogProvider.getBootLog();

  private static final String INSTANCE_UUID_SEPARATOR = "#";

  private static final String DEV = "dev";

  private static final String SH = "sh";

  private static final String TSH = "tsh";

  private static final String BETA = "beta";

  /**
   * 私有构造函数
   */
  private InstanceInfoInitializer() {
    super();
  }

  public static void initial() {
    String agentVersion = InstanceInfoInitializer.class.getPackage().getImplementationVersion();
    String hostname = IpUtil.getLocalHostname();
    String ipv4 = IpUtil.getLocalIpv4Addr();
    Long startTimestamp = RuntimeMxUtil.getStartTime();
    Integer pid = RuntimeMxUtil.getPid();
    String applicationName = Configs.Instance.APP_NAME.getProp();
    Properties fileProperties = getAppEnvProps();
    String set = getFileProp(fileProperties, Configs.Instance.SET);
    // 这里使用 对象地址判断是否相等
    // 优先使用set，其次使用IDC
    if (Configs.Instance.SET.getValue().equals(set)) {
      set = getFileProp(fileProperties, Configs.Instance.IDC);
    }
    String env = getFileProp(fileProperties, Configs.Instance.ENV);
    String deployEnv = getFileProp(fileProperties, Configs.Instance.DEPLOY_ENV);
    String instanceUuid =
        applicationName + INSTANCE_UUID_SEPARATOR + startTimestamp + INSTANCE_UUID_SEPARATOR + ipv4
            + INSTANCE_UUID_SEPARATOR + set;
    String swimLane = getFileProp(fileProperties, Configs.Instance.SWIM_LANE);
    String chiruGoServer = getFileProp(fileProperties, Configs.Instance.CHIRU_GO_SERVER);
    Environment environment = getEnvironment(env, set, deployEnv);
    DefaultInstanceInfo instanceInfo = new DefaultInstanceInfo(agentVersion, instanceUuid, hostname,
        ipv4, applicationName, startTimestamp, set, env, deployEnv, environment, swimLane, pid);
    instanceInfo.setStatus(InstanceStatus.START.id);
    LOG.info(" 启动服务：{}", instanceInfo);
    AgentLogUtil.log(" 启动服务：" + instanceInfo);
    if (chiruGoServer != null) {
      instanceInfo.addAttach(Configs.Instance.CHIRU_GO_SERVER.getKey(), chiruGoServer);
    }
    instanceInfo.addAttach(Configs.Instance.ENVIRONMENT.getKey(), environment.name());
    FinalFieldUtil.setValue(InstanceInfo.class, AgentCst.INSTANCE_FIELD_NAME, instanceInfo);

  }

  public static Properties getAppEnvProps() {
    Properties props = new Properties();
    File appEnvFile = new File(Configs.Instance.APP_ENV_FILE.getProp());
    try (FileInputStream fis = new FileInputStream(appEnvFile)) {
      props.load(fis);
      LOG.debug("本地环境配置文件：读取Prop {} 条", props.size());
    } catch (Exception e) {
      LOG.error("本地环境配置文件：读取Prop发生异常。", e);
    }
    return props;
  }

  protected static Environment getEnvironment(String env, String set, String deployEnv) {
    if (DEV.equalsIgnoreCase(deployEnv)) {
      return Environment.DEV;
    }
    if (set == null) {
      LOG.info("请在本地环境配置正确的deployenv、env和set");
      throw new AgentException("请在本地环境配置正确的deployenv、env和set");
    }
    String lowCaseSet = set.toLowerCase();
    if (lowCaseSet.startsWith(SH)) {
      return Environment.PRO;
    }
    if (lowCaseSet.startsWith(TSH) || lowCaseSet.equalsIgnoreCase(BETA)) {
      return Environment.FAT;
    }
    LOG.info("请在本地环境配置正确的deployenv、env和set");
    throw new AgentException("请在本地环境配置正确的deployenv、env和set");
  }

  private static String getFileProp(Properties fileProperties, KvmConfig configKvm) {
    String config = ConfigProvider.INSTANCE.fromAll(configKvm.getKey());
    if (config != null) {
      return config;
    }
    return fileProperties.getProperty(configKvm.getMagic(), configKvm.getValue());
  }

}

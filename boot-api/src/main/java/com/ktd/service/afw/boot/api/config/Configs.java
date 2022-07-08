package com.ktd.service.afw.boot.api.config;

/**
 * 配置的常量
 */
public final class Configs {

  /**
   * 未知
   */
  public static final String UNKNOWN = "UNKNOWN";

  /**
   * 无参私有构造函数
   */
  private Configs() {
    super();
  }

  public static final class Enhance {

    public static final KvConfig CLASS_OUTPUT_SWITCH = KvConfigFactory.of(
        "afw.enhance.class.output.switch", "false");

    public static final KvConfig CLASS_OUTPUT_DIR = KvConfigFactory.of(
        "afw.enhance.class.output.dir", null);

    public static final KvConfig EXCLUDE_ENHANCE_CLASSES = KvConfigFactory.of(
        "afw.enhance.exclude.enhance.classes", null);

    public static final KvConfig DISABLE_INJECTOR_DEFINES = KvConfigFactory.of(
        "afw.enhance.disable.injector.defines", null);

    public static final KvConfig ENHANCE_METHOD_PACKAGE_NAME_PREFIXES = KvConfigFactory.of(
        "afw.enhance.method.package.name.prefixes", null);

    public static final KvConfig ENHANCE_METHOD_MATCH_ONOFF = KvConfigFactory.of(
        "afw.enhance.method.match.onoff", "false");

    private Enhance() {
      super();
    }
  }

  public static final class Instance {

    public static final KvConfig APP_ENV_FILE = KvConfigFactory.of("APP_ENV_FILE",
        "/data/webapps/appenv");

    /**
     * 应用名称
     */
    public static final KvConfig APP_NAME = KvConfigFactory.of("app.name", UNKNOWN);

    // [BEG] 本地文件中的属性 == BEG == BEG == BEG == BEG == BEG == BEG == BEG == BEG == BEG == BEG == BEG == BEG ==
    /**
     * Key: System.key Value: Default Value Magic: File.Key
     */
    public static final KvmConfig IDC = KvConfigFactory.of("APP_IDC", UNKNOWN, "idc");

    public static final KvmConfig SET = KvConfigFactory.of("APP_SET", UNKNOWN, "set");

    public static final KvmConfig ENV = KvConfigFactory.of("APP_ENV", UNKNOWN, "env");

    public static final KvmConfig DEPLOY_ENV = KvConfigFactory.of("APP_DEPLOY_ENV", UNKNOWN,
        "deployenv");

    public static final KvmConfig SWIM_LANE = KvConfigFactory.of("APP_SWIM_LANE", UNKNOWN,
        "swimlane");

    public static final KvmConfig CHIRU_GO_SERVER = KvConfigFactory.of("APP_CHIRU_GO_SERVER", null,
        "chiru_go_server");

    public static final KvConfig ENVIRONMENT = KvConfigFactory.of("afw.instance.environment",
        null);

    public static final KvConfig ERROR_REPORT_HTTP_URL = KvConfigFactory.of(
        "afw.instance.error.report.http.url", null);

    // [END] 本地文件中的属性 == END == END == END == END == END == END == END == END == END == END == END == END ==

    private Instance() {
      super();
    }
  }

  public static final class Component {

    /**
     * 不启用该组件
     */
    public static final KvConfig DISABLE_CLASSES = KvConfigFactory.of(
        "afw.component.disable.classes", null);

    /**
     * 私有构造函数
     */
    private Component() {
      super();
    }
  }

  public static final class Schedule {

    public static final KvConfig POOL_CORE_SIZE = KvConfigFactory.of(
        "afw.schedule.pool.core.size", "2");

    public static final KvConfig DETECT_METRIC_REPORTER_RATE_MILLS = KvConfigFactory.of(
        "afw.schedule.detect.metric.reporter.rate.mills", "60000");

    /**
     * Http Component NIO Pool统计间隔
     */
    public static final KvConfig HC_NIO_POOL_RATE_MILLS = KvConfigFactory.of(
        "afw.schedule.metric.report.hc.nio.pool.rate.mills", "60000");

    /**
     * 私有构造函数
     */
    private Schedule() {
      super();
    }
  }

  /**
   * 日志相关的配置
   */
  public static final class Log {

    /**
     * Log是否包含Exception的栈
     */
    public static final KvConfig CONTAIN_STACK = KvConfigFactory.of("afw.log.contain.stack",
        "false");

    /**
     * MAX_STACK
     */
    public static final KvConfig MAX_STACK = KvConfigFactory.of("afw.log.max.stack", "30");

    /**
     * Log的level
     */
    public static final KvConfig LEVEL = KvConfigFactory.of("afw.log.level", "WARN");

    /**
     * 无参私有构造函数
     */
    private Log() {
      super();
    }

  }
}

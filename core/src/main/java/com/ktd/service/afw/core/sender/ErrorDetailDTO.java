package com.ktd.service.afw.core.sender;

import java.util.StringJoiner;

/**
 * 发生异常的详细信息
 */
public class ErrorDetailDTO {

  private Long timestamp;

  private String ipv4;

  private String hostname;

  private String applicationName;

  private String agentVersion;

  /**
   * 环境信息
   */
  private String env;

  /**
   * 机房信息
   */
  private String set;

  /**
   *
   */
  private String deployEnv;

  /**
   * 当前线程的PID
   */
  private Integer pid;

  /**
   * 当前的TraceUuid
   */
  private String traceUuid;

  private ErrorInfoDTO errorInfo;

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public String getIpv4() {
    return ipv4;
  }

  public void setIpv4(String ipv4) {
    this.ipv4 = ipv4;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public String getApplicationName() {
    return applicationName;
  }

  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  public String getAgentVersion() {
    return agentVersion;
  }

  public void setAgentVersion(String agentVersion) {
    this.agentVersion = agentVersion;
  }

  public String getEnv() {
    return env;
  }

  public void setEnv(String env) {
    this.env = env;
  }

  public String getSet() {
    return set;
  }

  public void setSet(String set) {
    this.set = set;
  }

  public String getDeployEnv() {
    return deployEnv;
  }

  public void setDeployEnv(String deployEnv) {
    this.deployEnv = deployEnv;
  }

  public Integer getPid() {
    return pid;
  }

  public void setPid(Integer pid) {
    this.pid = pid;
  }

  public String getTraceUuid() {
    return traceUuid;
  }

  public void setTraceUuid(String traceUuid) {
    this.traceUuid = traceUuid;
  }

  public ErrorInfoDTO getErrorInfo() {
    return errorInfo;
  }

  public void setErrorInfo(ErrorInfoDTO errorInfo) {
    this.errorInfo = errorInfo;
  }

  /**
   * 这里需要返回JSON型的字符串
   */
  @Override
  public String toString() {
    return new StringJoiner(", ", "{", "}")
        .add("'timestamp':" + timestamp)
        .add("'ipv4':'" + ipv4 + "'")
        .add("'hostname':'" + hostname + "'")
        .add("'applicationName':'" + applicationName + "'")
        .add("'agentVersion':'" + agentVersion + "'")
        .add("'env':'" + env + "'")
        .add("'set':'" + set + "'")
        .add("'deployEnv':'" + deployEnv + "'")
        .add("'pid':" + pid)
        .add("'traceUuid':'" + traceUuid + "'")
        .add("'errorInfo':" + errorInfo)
        .toString();
  }
}

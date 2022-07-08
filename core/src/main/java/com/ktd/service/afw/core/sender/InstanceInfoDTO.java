package com.ktd.service.afw.core.sender;

import java.io.Serializable;
import java.util.Map;

/**
 * 应用节点信息
 */
public class InstanceInfoDTO implements Serializable {

  /**
   * Agent的版本
   */
  private String agentVersion;

  /**
   * 应用节点的唯一ID
   */
  private String instanceUuid;

  /**
   * 应用节点所在容器的主机
   */
  private String hostname;

  /**
   * 应用节点的IPv4地址
   */
  private String ipv4;

  /**
   * 应用名
   */
  private String applicationName;

  /**
   * 应用节点启动时间戳
   */
  private Long startTimestamp;

  /**
   * 应用节点发送该条数据的时间戳
   */
  private Long timestamp;

  /**
   * 运行状态
   */
  private Short instanceStatus;

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
   * 错误信息
   */
  private ErrorInfoDTO errorInfo;

  private Map<String, String> attachMap;

  public String getAgentVersion() {
    return agentVersion;
  }

  public void setAgentVersion(String agentVersion) {
    this.agentVersion = agentVersion;
  }

  public String getInstanceUuid() {
    return instanceUuid;
  }

  public InstanceInfoDTO setInstanceUuid(String instanceUuid) {
    this.instanceUuid = instanceUuid;
    return this;
  }

  public String getHostname() {
    return hostname;
  }

  public InstanceInfoDTO setHostname(String hostname) {
    this.hostname = hostname;
    return this;
  }

  public String getIpv4() {
    return ipv4;
  }

  public InstanceInfoDTO setIpv4(String ipv4) {
    this.ipv4 = ipv4;
    return this;
  }

  public String getApplicationName() {
    return applicationName;
  }

  public InstanceInfoDTO setApplicationName(String applicationName) {
    this.applicationName = applicationName;
    return this;
  }

  public Long getStartTimestamp() {
    return startTimestamp;
  }

  public InstanceInfoDTO setStartTimestamp(Long startTimestamp) {
    this.startTimestamp = startTimestamp;
    return this;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public InstanceInfoDTO setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public Short getInstanceStatus() {
    return instanceStatus;
  }

  public InstanceInfoDTO setInstanceStatus(Short instanceStatus) {
    this.instanceStatus = instanceStatus;
    return this;
  }

  public String getEnv() {
    return env;
  }

  public InstanceInfoDTO setEnv(String env) {
    this.env = env;
    return this;
  }

  public String getSet() {
    return set;
  }

  public InstanceInfoDTO setSet(String set) {
    this.set = set;
    return this;
  }

  public String getDeployEnv() {
    return deployEnv;
  }

  public InstanceInfoDTO setDeployEnv(String deployEnv) {
    this.deployEnv = deployEnv;
    return this;
  }

  public Integer getPid() {
    return pid;
  }

  public InstanceInfoDTO setPid(Integer pid) {
    this.pid = pid;
    return this;
  }

  public ErrorInfoDTO getErrorInfo() {
    return errorInfo;
  }

  public InstanceInfoDTO setErrorInfo(ErrorInfoDTO errorInfo) {
    this.errorInfo = errorInfo;
    return this;
  }

  public Map<String, String> getAttachMap() {
    return attachMap;
  }

  public InstanceInfoDTO setAttachMap(Map<String, String> attachMap) {
    this.attachMap = attachMap;
    return this;
  }

  @Override
  public String toString() {
    return "InstanceInfoDTO{" +
        "agentVersion='" + agentVersion + '\'' +
        ", instanceUuid='" + instanceUuid + '\'' +
        ", hostname='" + hostname + '\'' +
        ", ipv4='" + ipv4 + '\'' +
        ", applicationName='" + applicationName + '\'' +
        ", startTimestamp=" + startTimestamp +
        ", timestamp=" + timestamp +
        ", instanceStatus=" + instanceStatus +
        ", env='" + env + '\'' +
        ", set='" + set + '\'' +
        ", deployEnv='" + deployEnv + '\'' +
        ", pid=" + pid +
        ", errorInfo=" + errorInfo +
        '}';
  }
}

package com.ktd.service.afw.core.model;

import java.util.Map;

/**
 * Instance信息接口
 */
public interface InstanceInfo {

  InstanceInfo INSTANCE = null;

  /**
   * 应用Agent的版本号
   */
  String getAgentVersion();

  /**
   * 该实例全局唯一UUID
   */
  String getUuid();

  /**
   * 应用的hostname
   */
  String getHostname();

  /**
   * 长整型的IPV4地址
   */
  String getIpv4();

  /**
   * ipv4的hash值，避免显示使用ipv4
   *
   * @return
   */
  String getIpv4HashValue();

  /**
   * 应用名称
   */
  String getApplicationName();

  /**
   * 实例启动时间
   */
  Long getStartTimestamp();

  /**
   * 获取集群信息 获取的是配置文件中的属性，优先级依次是`set`->`idc` 获取数据中心(例: sh-1, sh-2)
   */
  String getSet();

  /**
   * 获取环境 获取的是配置文件中的属性，env：获取优先级依次是`env` 获取运行环境(例: prod, staging)
   */
  String getEnv();

  /**
   * 获取deployEnv 获取部署环境
   */
  String getDeployEnv();

  /**
   * 获取当前的环境信息
   */
  Environment getEnvironment();

  /**
   * 泳道
   */
  String getSwimLane();

  /**
   * 获取当前的Pid
   */
  Integer getPid();

  /**
   * Status
   */
  Short getStatus();

  Throwable getError();

  Map<String, String> getAttachMap();

  void addAttach(String attachKey, String attachValue);

  String removeAttach(String attachKey);

}

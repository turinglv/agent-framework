package com.ktd.service.afw.boot.api.core;

import com.ktd.service.afw.boot.api.util.IpUtil;
import com.ktd.service.afw.core.model.Environment;
import com.ktd.service.afw.core.model.InstanceInfo;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Instance信息
 *
 */
public class DefaultInstanceInfo implements InstanceInfo {

    /**
     * Agent的版本
     */
    private final String agentVersion;
    /**
     * 应用节点的唯一ID
     */
    private final String uuid;

    /**
     * 应用节点所在容器的主机
     */
    private final String hostname;

    /**
     * 应用节点的IPv4地址
     */
    private final String ipv4;

    private String ipv4HashValue;

    /**
     * 应用名
     */
    private final String applicationName;

    /**
     * 应用节点启动时间戳
     */
    private final Long startTimestamp;

    /**
     * 环境信息
     */
    private final String set;

    /**
     * 机房信息
     */
    private final String env;

    /**
     * deployEnv
     */
    private final String deployEnv;

    private final Environment environment;
    /**
     * 泳道
     */
    private final String swimLane;

    /**
     * 当前线程的PID
     */
    private final Integer pid;

    /**
     * 运行状态
     */
    private volatile Short status;

    private volatile Throwable error;

    private final Map<String, String> attachMap;

    public DefaultInstanceInfo(String agentVersion, String uuid, String hostname, String ipv4, String applicationName, Long startTimestamp, String set, String env, String deployEnv, Environment environment, String swimLane, Integer pid) {
        this.agentVersion = agentVersion;
        this.uuid = uuid;
        this.hostname = hostname;
        this.ipv4 = ipv4;
        this.applicationName = applicationName;
        this.startTimestamp = startTimestamp;
        this.set = set;
        this.env = env;
        this.deployEnv = deployEnv;
        this.environment = environment;
        this.swimLane = swimLane;
        this.pid = pid;
        this.attachMap = new HashMap<>();
        try {
            Long ipv4Long = IpUtil.convertIpv4(ipv4);
            if (ipv4Long == null) {
                this.ipv4HashValue = null;
            } else {
                this.ipv4HashValue = ipv4Long.toString();
            }
        } catch (Exception e) {
            this.ipv4HashValue = ipv4;
        }
    }

    @Override
    public String getAgentVersion() {
        return agentVersion;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public String getHostname() {
        return hostname;
    }

    @Override
    public String getIpv4() {
        return ipv4;
    }

    @Override
    public String getIpv4HashValue() {
        return ipv4HashValue;
    }

    @Override
    public String getApplicationName() {
        return applicationName;
    }

    @Override
    public Long getStartTimestamp() {
        return startTimestamp;
    }

    @Override
    public String getSet() {
        return set;
    }

    @Override
    public String getEnv() {
        return env;
    }

    @Override
    public String getDeployEnv() {
        return this.deployEnv;
    }

    @Override
    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public String getSwimLane() {
        return swimLane;
    }

    @Override
    public Integer getPid() {
        return pid;
    }

    @Override
    public Short getStatus() {
        return status;
    }

    public Throwable getError() {
        return error;
    }

    @Override
    public Map<String, String> getAttachMap() {
        return Collections.unmodifiableMap(attachMap);
    }

    public DefaultInstanceInfo setStatus(Short status) {
        this.status = status;
        return this;
    }

    public DefaultInstanceInfo setError(Throwable error) {
        this.error = error;
        return this;
    }

    public void addAttach(String attachKey, String attachValue) {
        attachMap.put(attachKey, attachValue);
    }

    public String removeAttach(String attachKey) {
        return attachMap.remove(attachKey);
    }

    @Override
    public String toString() {
        return "DefaultInstanceInfo{" +
                "agentVersion='" + agentVersion + '\'' +
                ", uuid='" + uuid + '\'' +
                ", hostname='" + hostname + '\'' +
                ", ipv4='" + ipv4 + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", startTimestamp=" + startTimestamp +
                ", set='" + set + '\'' +
                ", env='" + env + '\'' +
                ", deployEnv='" + deployEnv + '\'' +
                ", environment=" + environment +
                ", swimLane='" + swimLane + '\'' +
                ", pid=" + pid +
                ", error=" + error +
                ", attachMap=" + attachMap +
                '}';
    }
}

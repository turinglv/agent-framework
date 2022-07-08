package com.ktd.service.afw.core.command;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultEvent implements Event, Serializable {

    private long timestamp;

    private String applicationName;

    private String action;

    private List<String> effectIps;

    private List<String> notEffectIps;

    private String componentIdr;

    private Map<String, String> headers;

    private Map<String, String> values;

    /**
     * 公共构造函数
     */
    public DefaultEvent() {
        super();
        headers = new HashMap<>();
        values = new HashMap<>();
    }

    public String putHeader(String headerKey, String headerValue) {
        return this.headers.put(headerKey, headerValue);
    }

    public String putValue(String key, String value) {
        return this.values.put(key, value);
    }

    @Override
    public long getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String getApplicationName() {
        return this.applicationName;
    }

    @Override
    public List<String> getEffectIps() {
        return this.effectIps;
    }

    @Override
    public List<String> getNotEffectIps() {
        return this.notEffectIps;
    }

    @Override
    public String getComponentIdr() {
        return componentIdr;
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public String getHeader(String headerKey) {
        return this.headers.get(headerKey);
    }

    @Override
    public Map<String, String> getValues() {
        return this.values;
    }

    @Override
    public String getValue(String key) {
        return this.values.get(key);
    }

    @Override
    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setEffectIps(List<String> effectIps) {
        this.effectIps = effectIps;
    }

    public void setNotEffectIps(List<String> notEffectIps) {
        this.notEffectIps = notEffectIps;
    }

    public void setComponentIdr(String componentIdr) {
        this.componentIdr = componentIdr;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }


    @Override
    public String toString() {
        return "DefaultEvent{" +
                "timestamp=" + timestamp +
                ", applicationName='" + applicationName + '\'' +
                ", action='" + action + '\'' +
                ", effectIps=" + effectIps +
                ", notEffectIps=" + notEffectIps +
                ", componentIdr='" + componentIdr + '\'' +
                ", headers=" + headers +
                ", values=" + values +
                '}';
    }
}

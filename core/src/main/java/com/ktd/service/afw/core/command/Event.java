package com.ktd.service.afw.core.command;

import java.util.List;
import java.util.Map;

public interface Event {

    long getTimestamp();

    String getApplicationName();

    List<String> getEffectIps();

    List<String> getNotEffectIps();

    String getComponentIdr();

    String getAction();

    Map<String, String> getHeaders();

    String getHeader(String headerKey);

    Map<String, String> getValues();

    String getValue(String key);

}

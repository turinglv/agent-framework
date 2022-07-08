package com.ktd.service.afw.boot.api.exception;

import com.ktd.service.afw.core.exception.AgentException;

/**
 * 缺失配置
 */
public class MissConfigException extends AgentException {

    public MissConfigException(String propName) {
        super("属性缺失：" + propName);
    }
}

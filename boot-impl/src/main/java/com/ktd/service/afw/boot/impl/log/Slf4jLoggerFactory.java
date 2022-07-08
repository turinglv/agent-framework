package com.ktd.service.afw.boot.impl.log;

import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLoggerFactory implements LogFactory {

    @Override
    public Log getLog(String name) {
        Logger logger = LoggerFactory.getLogger(name);
        return logger == null ? BootLog.getInstance() : new Slf4jLogger(logger);
    }

    @Override
    public Log getLog(Class<?> clazz) {
        Logger logger = LoggerFactory.getLogger(clazz);
        return logger == null ? BootLog.getInstance() : new Slf4jLogger(logger);
    }
}

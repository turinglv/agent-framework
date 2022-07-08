package com.ktd.service.afw.boot.impl.log;

import com.ktd.service.afw.boot.api.log.Log;
import org.slf4j.Logger;

public class Slf4jLogger implements Log {

    private final Logger slf4j;

    public Slf4jLogger(Logger slf4j) {
        this.slf4j = slf4j;
    }

    @Override
    public void debug(String message) {
        slf4j.debug(message);
    }

    @Override
    public void debug(String template, Object object) {
        slf4j.debug(template, object);
    }

    @Override
    public void debug(String template, Object... objects) {
        slf4j.debug(template, objects);
    }

    @Override
    public void debug(String message, Throwable throwable) {
        slf4j.debug(message, throwable);
    }

    @Override
    public void info(String message) {
        slf4j.info(message);
    }

    @Override
    public void info(String template, Object object) {
        slf4j.info(template, object);
    }

    @Override
    public void info(String template, Object... objects) {
        slf4j.info(template, objects);

    }

    @Override
    public void info(String message, Throwable throwable) {
        slf4j.info(message, throwable);
    }

    @Override
    public void warn(String message) {
        slf4j.warn(message);
    }

    @Override
    public void warn(String template, Object object) {
        slf4j.warn(template, object);
    }

    @Override
    public void warn(String template, Object... objects) {
        slf4j.warn(template, objects);
    }

    @Override
    public void warn(String message, Throwable throwable) {
        slf4j.warn(message, throwable);
    }

    @Override
    public void error(String message) {
        slf4j.error(message);
    }

    @Override
    public void error(String template, Object object) {
        slf4j.error(template, object);
    }

    @Override
    public void error(String template, Object... objects) {
        slf4j.error(template, objects);
    }

    @Override
    public void error(String message, Throwable throwable) {
        slf4j.error(message, throwable);
    }
}

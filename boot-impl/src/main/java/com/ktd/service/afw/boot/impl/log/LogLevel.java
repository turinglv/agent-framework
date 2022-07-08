package com.ktd.service.afw.boot.impl.log;

/**
 * 日志的级别
 */
public enum LogLevel {

    TRACE(10, "TRACE"),

    DEBUG(10, "DEBUG"),

    INFO(20, "INFO"),

    WARN(30, "WARN"),

    ERROR(40, "ERROR");

    private int levelInt;

    private String levelStr;

    LogLevel(int i, String s) {
        levelInt = i;
        levelStr = s;
    }

    public static int toInt(String level) {
        if (level == null) {
            return ERROR.levelInt;
        }
        try {
            return LogLevel.valueOf(level.toUpperCase()).levelInt;
        } catch (Exception e) {
            return INFO.levelInt;
        }

    }

    public int toInt() {
        return levelInt;
    }

    public String toString() {
        return levelStr;
    }

}

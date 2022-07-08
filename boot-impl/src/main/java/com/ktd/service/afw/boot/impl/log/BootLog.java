package com.ktd.service.afw.boot.impl.log;

import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.util.ExceptionUtil;
import com.ktd.service.afw.boot.api.util.SafeDateFormatUtil;
import java.io.PrintStream;
import java.util.Date;

/**
 * 默认的系统输出，当Log还没有启动时，返回该Log
 */
public class BootLog implements Log {

    /**
     * Boot日志记录器
     */
    private static final BootLog INSTANCE = new BootLog("BOOT", System.out, System.out);
    /**
     * 日志级别
     */
    private static volatile int logLevel = LogLevel.DEBUG.toInt();
    /**
     * 日志名称
     */
    private final String name;

    /**
     * 标准输出流
     */
    private final PrintStream stdOut;

    /**
     * 错误输出流
     */
    private final PrintStream errOut;

    /**
     * 根据日志级别和输出创建boot log
     *
     * @param name   日志名称
     * @param stdOut 标准输出流
     * @param errOut 错误输出流
     */
    private BootLog(String name, PrintStream stdOut, PrintStream errOut) {
        this.name = name;
        this.stdOut = stdOut;
        this.errOut = errOut;
    }

    public static Log getInstance() {
        return INSTANCE;
    }

    /**
     * 设置Boot的日志级别
     *
     * @param logLevel Log级别
     */
    public static void setLogLevel(final int logLevel) {
        BootLog.logLevel = logLevel;
    }

    @Override
    public void debug(String message) {
        this.log(stdOut, LogLevel.DEBUG, message);

    }

    @Override
    public void debug(String template, Object object) {
        this.log(stdOut, LogLevel.DEBUG, template, object);
    }

    @Override
    public void debug(String template, Object... objects) {
        this.log(stdOut, LogLevel.DEBUG, template, objects);
    }

    @Override
    public void debug(String message, Throwable throwable) {
        this.log(stdOut, LogLevel.DEBUG, message, throwable);
    }

    @Override
    public void info(String message) {
        this.log(stdOut, LogLevel.INFO, message);

    }

    @Override
    public void info(String template, Object object) {
        this.log(stdOut, LogLevel.INFO, template, object);
    }

    @Override
    public void info(String template, Object... objects) {
        this.log(stdOut, LogLevel.INFO, template, objects);
    }

    @Override
    public void info(String message, Throwable throwable) {
        this.log(stdOut, LogLevel.INFO, message, throwable);
    }

    @Override
    public void warn(String message) {
        this.log(stdOut, LogLevel.WARN, message);

    }

    @Override
    public void warn(String template, Object object) {
        this.log(stdOut, LogLevel.WARN, template, object);
    }

    @Override
    public void warn(String template, Object... objects) {
        this.log(stdOut, LogLevel.WARN, template, objects);
    }

    @Override
    public void warn(String message, Throwable throwable) {
        this.log(stdOut, LogLevel.WARN, message, throwable);
    }

    @Override
    public void error(String message) {
        this.log(errOut, LogLevel.ERROR, message);

    }

    @Override
    public void error(String template, Object object) {
        this.log(errOut, LogLevel.ERROR, template, object);
    }

    @Override
    public void error(String template, Object... objects) {
        this.log(errOut, LogLevel.ERROR, template, objects);
    }

    @Override
    public void error(String message, Throwable throwable) {
        this.log(errOut, LogLevel.ERROR, message, throwable);
    }

    /**
     * 记录普通的message信息
     *
     * @param level   日志级别{@link LogLevel}
     * @param message 日志信息
     */
    private void log(PrintStream ps, LogLevel level, String message) {
        if (level.toInt() < logLevel) {
            return;
        }
        ps.println(" AFW " + name + " " + level + " " + SafeDateFormatUtil.formatLongDateTimeMs(new Date()) + " "
                + message);
    }

    private void log(PrintStream ps, LogLevel level, String template, Object object) {
        if (level.toInt() < logLevel) {
            return;
        }

        ps.println(" AFW " + name + " " + level + " " + SafeDateFormatUtil.formatLongDateTimeMs(new Date()) + " "
                + MessageFormatter.format(template, object).getMessage());
    }

    private void log(PrintStream ps, LogLevel level, String template, Object... objects) {
        if (level.toInt() < logLevel) {
            return;
        }

        if (objects != null && objects.length > 0 && objects[objects.length - 1] instanceof Throwable) {
            // 如果最后一个参数是Throwable，则将Throwable单独在追加一边
            ps.println(" AFW " + name + " " + level + " " + SafeDateFormatUtil.formatLongDateTimeMs(new Date()) + " "
                    + MessageFormatter.format(template, objects).getMessage() + System.lineSeparator()
                    + ExceptionUtil.getStack((Throwable) objects[objects.length - 1]));
        } else {
            // 将参数format后输出
            ps.println(" AFW " + name + " " + level + " " + SafeDateFormatUtil.formatLongDateTimeMs(new Date()) + " "
                    + MessageFormatter.format(template, objects).getMessage());
        }
    }

    private void log(PrintStream ps, LogLevel level, String message, Throwable throwable) {
        if (level.toInt() < logLevel) {
            return;
        }
        ps.println(" AFW " + name + " " + level + " " + SafeDateFormatUtil.formatLongDateTimeMs(new Date()) + " "
                + message + System.lineSeparator() + ExceptionUtil.getStack(throwable));
    }

}

package com.ktd.service.afw.boot.api.util;

import com.ktd.service.afw.core.exception.AgentException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 线程安全的DateFormat
 *
 */
public final class SafeDateFormatUtil {

    /**
     * 长型，带毫秒
     */
    private static final String LONG_DATE_TIME_MS = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final ThreadLocal<SimpleDateFormat> LONG_DATE_TIME_MS_SDF = new DateFormat(LONG_DATE_TIME_MS);
    /**
     * 长型，不带毫秒
     */
    private static final String LONG_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    private static final ThreadLocal<SimpleDateFormat> LONG_DATE_TIME_SDF = new DateFormat(LONG_DATE_TIME);
    /**
     * 长型，日期
     */
    private static final String LONG_DATE = "yyyy-MM-dd";

    private static final ThreadLocal<SimpleDateFormat> LONG_DATE_SDF = new DateFormat(LONG_DATE);
    /**
     * 长型，毫秒
     */
    private static final String LONG_TIME_MS = "HH:mm:ss.SSS";

    private static final ThreadLocal<SimpleDateFormat> LONG_TIME_MS_SDF = new DateFormat(LONG_TIME_MS);
    /**
     * 长型，秒
     */
    private static final String LONG_TIME = "HH:mm:ss";

    private static final ThreadLocal<SimpleDateFormat> LONG_TIME_SDF = new DateFormat(LONG_TIME);

    /**
     * 无参私有构造函数
     */
    private SafeDateFormatUtil() {
        super();
    }

    public static Date parseLongDateTimeMs(String dateStr) {
        try {
            return LONG_DATE_TIME_MS_SDF.get().parse(dateStr);
        } catch (ParseException e) {
            throw new AgentException("日期格式错误:" + dateStr, e);
        }
    }

    public static Date parseLongDateTime(String dateStr) {
        try {
            return LONG_DATE_TIME_SDF.get().parse(dateStr);
        } catch (ParseException e) {
            throw new AgentException("日期格式错误:" + dateStr, e);
        }
    }

    public static Date parseLongDate(String dateStr) {
        try {
            return LONG_DATE_SDF.get().parse(dateStr);
        } catch (ParseException e) {
            throw new AgentException("日期格式错误:" + dateStr, e);
        }
    }

    public static Date parseLongTimeMs(String dateStr) {
        try {
            return LONG_TIME_MS_SDF.get().parse(dateStr);
        } catch (ParseException e) {
            throw new AgentException("日期格式错误:" + dateStr, e);
        }
    }

    public static Date parseLongTime(String dateStr) {
        try {
            return LONG_TIME_SDF.get().parse(dateStr);
        } catch (ParseException e) {
            throw new AgentException("日期格式错误:" + dateStr, e);
        }
    }

    public static String formatLongDateTimeMs(Date date) {
        return LONG_DATE_TIME_MS_SDF.get().format(date);
    }

    public static String formatLongDateTime(Date date) {
        return LONG_DATE_TIME_SDF.get().format(date);
    }

    public static String formatLongDate(Date date) {
        return LONG_DATE_SDF.get().format(date);
    }

    public static String formatLongTimeMs(Date date) {
        return LONG_TIME_MS_SDF.get().format(date);
    }

    public static String formatLongTime(Date date) {
        return LONG_TIME_SDF.get().format(date);
    }

    private static class DateFormat extends ThreadLocal<SimpleDateFormat> {

        private final String pattern;

        public DateFormat(String pattern) {
            super();
            this.pattern = pattern;
        }

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(pattern);
        }
    }

}


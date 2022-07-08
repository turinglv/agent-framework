package com.ktd.service.afw.boot.api.util;

public final class StringUtil {

    /**
     * 私有构造函数
     */
    private StringUtil() {
        super();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String join(final char delimiter, final Object... objects) {
        if (objects.length == 0 || (objects.length == 1 && objects[0] == null)) {
            return null;
        }
        if (objects.length == 1) {
            return objects[0].toString();
        }
        final StringBuilder sb = new StringBuilder();
        if (objects[0] != null) {
            sb.append(objects[0]);
        }
        for (int i = 1; i < objects.length; ++i) {
            if (objects[i] != null && !objects.toString().isEmpty()) {
                sb.append(delimiter).append(objects[i]);
            } else {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    public static final String getOriginString(Object object) {
        if (object == null) {
            return null;
        }
        return object.getClass().getName() + "@" + System.identityHashCode(object);
    }

}

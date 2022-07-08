package com.ktd.service.afw.boot.api.command;

import java.util.HashMap;
import java.util.Map;

public enum EventAction {

    OPEN,

    CLOSE,

    REFRESH,

    OTHER;

    private static final Map<String, EventAction> NAME_MAP = new HashMap<>();

    static {
        for (EventAction eventAction : EventAction.values()) {
            NAME_MAP.put(eventAction.name().toLowerCase(), eventAction);
        }
    }

    public static EventAction fromName(String name) {
        if (name == null) {
            return OTHER;
        }
        return NAME_MAP.getOrDefault(name.toLowerCase(), OTHER);
    }
}

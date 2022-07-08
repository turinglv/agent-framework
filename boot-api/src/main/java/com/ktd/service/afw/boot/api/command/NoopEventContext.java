package com.ktd.service.afw.boot.api.command;

import com.ktd.service.afw.core.command.Event;

public class NoopEventContext implements EventListener, EventContext {

    private static final String IDR = "noop";

    @Override
    public Long getLastHandleId() {
        return 0L;
    }

    @Override
    public boolean handle(Event event) {
        return false;
    }

    @Override
    public boolean supportsEvent(Event event) {
        return false;
    }

    @Override
    public void addListener(EventListener eventListener) {

    }

    @Override
    public int removeListener(EventListener eventListener) {
        return 0;
    }

    @Override
    public int removeListener(String idr) {
        return 0;
    }
}

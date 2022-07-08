package com.ktd.service.afw.boot.api.command;

public interface EventContext {

    EventContext INSTANCE = new NoopEventContext();

    void addListener(EventListener eventListener);

    int removeListener(EventListener eventListener);

    int removeListener(String idr);

}

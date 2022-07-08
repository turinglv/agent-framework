package com.ktd.service.afw.boot.api.command;


import com.ktd.service.afw.core.anno.source.CannotThrow;
import com.ktd.service.afw.core.anno.source.ThreadSafe;
import com.ktd.service.afw.core.api.Idrable;
import com.ktd.service.afw.core.command.Event;

@ThreadSafe("目前需要保证Event的并发线程访问的安全性")
public interface EventListener extends Idrable {

    Long getLastHandleId();

    @CannotThrow
    boolean handle(Event event);

    boolean supportsEvent(Event event);

}

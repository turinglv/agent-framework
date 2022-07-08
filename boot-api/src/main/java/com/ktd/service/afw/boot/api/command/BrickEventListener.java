package com.ktd.service.afw.boot.api.command;

import com.ktd.service.afw.boot.api.core.Brick;
import com.ktd.service.afw.boot.api.log.AgentErrorContext;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.core.anno.source.CannotThrow;
import com.ktd.service.afw.core.command.Event;

public interface BrickEventListener<T extends Brick> extends EventListener {

    Log LOG = LogProvider.getBootLog();

    T getTarget();

    void setLastHandleId(Long lastHandleId);

    @Override
    default String getIdr() {
        return this.getTarget().getClass().getName();
    }

    default void handleOpen(Event event) {
        LOG.debug("[EVENT] {}接收到Open:Event:{}", this.getTarget().getClass(), event);
        this.getTarget().on();
    }

    default void handleClose(Event event) {
        LOG.debug("[EVENT] {}接收到Close:Event:{}", this.getTarget().getClass(), event);
        this.getTarget().off();
    }

    default void handleRefresh(Event event) {
        LOG.debug("[EVENT] {}接收到Refresh:Event:{}", this.getTarget().getClass(), event);
        this.getTarget().refresh(event.getValues());
    }

    default void handleOther(Event event) {
        LOG.error("[EVENT] {}接收到Other:Event:{}", this.getTarget().getClass(), event);
    }

    @Override
    @CannotThrow
    default boolean handle(Event event) {
        try {
            EventAction eventAction = EventAction.fromName(event.getAction());
            switch (eventAction) {
                case OPEN:
                    this.handleOpen(event);
                    break;
                case CLOSE:
                    this.handleClose(event);
                    break;
                case REFRESH:
                    this.handleRefresh(event);
                    break;
                case OTHER:
                default:
                    this.handleOther(event);
                    break;
            }
            this.setLastHandleId(event.getTimestamp());
        } catch (Exception e) {
            LOG.error("处理Event发生异常：{}", event, e);
            AgentErrorContext.asyncReport(e, "Listener处理Event发生异常：" + event);
            return false;
        }
        return true;
    }

    @Override
    default boolean supportsEvent(Event event) {
        return this.getIdr().equals(event.getComponentIdr());
    }
}

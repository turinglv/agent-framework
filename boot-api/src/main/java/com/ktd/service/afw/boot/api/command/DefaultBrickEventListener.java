package com.ktd.service.afw.boot.api.command;

import com.ktd.service.afw.boot.api.core.Brick;

public class DefaultBrickEventListener<T extends Brick> implements BrickEventListener<T> {

    private final T target;

    private final String idr;

    private long lastHandleId;

    public DefaultBrickEventListener(T target) {
        this.target = target;
        this.idr = target.getClass().getName();
        this.lastHandleId = 0;
    }

    @Override
    public String getIdr() {
        return this.idr;
    }

    @Override
    public T getTarget() {
        return this.target;
    }

    @Override
    public void setLastHandleId(Long lastHandleId) {
        this.lastHandleId = lastHandleId;
    }

    @Override
    public Long getLastHandleId() {
        return this.lastHandleId;
    }

}

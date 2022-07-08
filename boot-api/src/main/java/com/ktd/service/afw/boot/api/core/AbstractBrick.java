package com.ktd.service.afw.boot.api.core;

import com.ktd.service.afw.boot.api.command.DefaultBrickEventListener;
import com.ktd.service.afw.boot.api.command.EventListener;
import java.util.Map;

public abstract class AbstractBrick implements Brick {

    /**
     * 开关默认开启
     */
    protected volatile boolean onOff;

    protected volatile EventListener listener;

    protected volatile String idr;

    public AbstractBrick() {
        this(true);
    }

    public AbstractBrick(boolean onOff) {
        this.onOff = onOff;
        this.idr = this.getClass().getName();
        this.listener = new DefaultBrickEventListener<>(this);
    }

    public AbstractBrick(boolean onOff, String idr, EventListener listener) {
        this.onOff = onOff;
        this.idr = idr;
        this.listener = listener;
    }

    @Override
    public void on() {
        this.onOff = true;
    }

    @Override
    public void off() {
        this.onOff = false;
    }

    @Override
    public boolean onOffState() {
        return onOff;
    }

    @Override
    public EventListener getListener() {
        return listener;
    }

    @Override
    public String getIdr() {
        return this.idr;
    }

    @Override
    public void refresh(Map<String, String> props) {
    }
}

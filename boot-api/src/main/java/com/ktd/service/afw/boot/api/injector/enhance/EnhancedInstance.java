package com.ktd.service.afw.boot.api.injector.enhance;

import org.springframework.aop.SpringProxy;

public interface EnhancedInstance extends SpringProxy {

    Object getAfwInjectField();

    void setAfwInjectField(Object value);
}

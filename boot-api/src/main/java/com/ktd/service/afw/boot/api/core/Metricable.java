package com.ktd.service.afw.boot.api.core;

public interface Metricable {

    default Object getMetrics() {
        return null;
    }

}

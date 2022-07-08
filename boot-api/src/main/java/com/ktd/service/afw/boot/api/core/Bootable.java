package com.ktd.service.afw.boot.api.core;

public interface Bootable {

    default void prepare() throws Exception {
    }

    default void boot() throws Exception {
    }

    default void onComplete() throws Exception {
    }

    default void shutdown() throws Exception {
    }
}

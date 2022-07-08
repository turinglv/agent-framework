package com.ktd.service.afw.boot.api.command;

public interface Listenable {

    default EventListener getListener() {
        return null;
    }

}

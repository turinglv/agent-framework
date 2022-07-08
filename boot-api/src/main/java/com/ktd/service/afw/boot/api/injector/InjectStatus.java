package com.ktd.service.afw.boot.api.injector;

public class InjectStatus {

    private boolean isMethodInjected = false;

    private boolean isFieldInjected = false;

    public boolean isMethodInjected() {
        return isMethodInjected;
    }

    public void injectMethodCompleted() {
        isMethodInjected = true;
    }

    public boolean isFieldInjected() {
        return isFieldInjected;
    }

    public void injectFieldCompleted() {
        isFieldInjected = true;
    }
}

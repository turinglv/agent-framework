package com.ktd.service.afw.boot.api.injector.enhance;

public class EnhancedMethodResult {

    private boolean isContinue = true;

    private Object result = null;

    private Throwable throwable = null;

    public void immediateReturn(Object result) {
        this.isContinue = false;
        this.result = result;
    }

    public void immediateThrow(Throwable throwable) {
        this.isContinue = false;
        this.throwable = throwable;
    }

    public boolean isContinue() {
        return isContinue;
    }

    public Object getDefineResult() {
        return result;
    }

    public Throwable getDefineThrowable() {
        return this.throwable;
    }
}

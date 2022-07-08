package com.ktd.service.afw.boot.api.injector.define;


import com.ktd.service.afw.boot.api.injector.point.StaticMethodInjectPoint;

public abstract class InstanceMethodInjectorDefine extends ClassInjectorDefine {

    @Override
    protected StaticMethodInjectPoint[] getStaticMethodsInjectPoints() {
        return null;
    }

}

package com.ktd.service.afw.boot.api.injector;

import com.ktd.service.afw.boot.api.core.Brick;
import com.ktd.service.afw.core.anno.source.ThreadSafe;

@ThreadSafe("目前Injector是多线程共用的，所以一定要保证并发的安全性")
public interface Injector extends Brick {

    int getId();
}

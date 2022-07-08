package com.ktd.service.afw.boot.api.injector;

import com.ktd.service.afw.boot.api.injector.enhance.EnhancedInstance;
import com.ktd.service.afw.boot.api.injector.enhance.EnhancedMethodResult;

public interface ConstructorInjector extends Injector {

    EnhancedMethodResult onConstruct(EnhancedInstance objInst, Object[] allArguments);

}

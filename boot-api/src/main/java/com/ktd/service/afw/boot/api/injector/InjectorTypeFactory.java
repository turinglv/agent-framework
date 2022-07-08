package com.ktd.service.afw.boot.api.injector;

import com.ktd.service.afw.boot.api.log.AgentErrorContext;
import com.ktd.service.afw.core.enums.FrameworkEnum;
import com.ktd.service.afw.core.exception.AgentException;
import com.ktd.service.afw.core.injector.InjectorType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class InjectorTypeFactory {

    private static final int GAP = 100;

    private static final Map<Integer, InjectorType> INJECTOR_TYPE_MAP = new ConcurrentHashMap<>();

    /**
     * 私有构造函数
     */
    private InjectorTypeFactory() {
        super();
    }

    public static InjectorType of(FrameworkEnum frameworkEnum, int number, String shortIdr, String idr) {
        return new InjectorType(frameworkEnum.code * GAP + number, shortIdr, idr);
    }


    public static boolean registerType(InjectorType injectorType) {
        InjectorType registeredType = INJECTOR_TYPE_MAP.put(injectorType.getId(), injectorType);
        boolean isRegistered = (registeredType == null);
        if (!isRegistered) {
            AgentErrorContext.asyncReport(new AgentException("注册相同Injector冲突：" + injectorType + ":" + registeredType), "注册相同Injector冲突");
        }
        return isRegistered;
    }

}

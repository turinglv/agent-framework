package com.ktd.service.afw.boot.api.injector;

import com.ktd.service.afw.boot.api.command.EventContext;
import com.ktd.service.afw.boot.api.command.EventListener;
import com.ktd.service.afw.boot.api.core.AgentContext;
import com.ktd.service.afw.boot.api.loader.LaunchedURLClassLoader;
import com.ktd.service.afw.core.exception.AgentException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class InjectorInstanceFactory {

    private static final ReentrantLock INSTANCE_LOAD_LOCK = new ReentrantLock();

    private static final ConcurrentHashMap<String, Object> INSTANCE_CACHE = new ConcurrentHashMap<String, Object>();

    private static final Map<ClassLoader, ClassLoader> INJECTOR_CLASSLOADER_MAP = new HashMap<ClassLoader, ClassLoader>();

    /**
     * 私有构造函数
     */
    private InjectorInstanceFactory() {
        super();
    }

    public static <T extends Injector> T create(String className, ClassLoader targetClassLoader) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (targetClassLoader == null) {
            throw new AgentException("必须指定injector的ClassLoader");
        }
        String instanceKey = className + "_OF_" + targetClassLoader.getClass().getName() + "@" + Integer.toHexString(targetClassLoader.hashCode());
        Object inst = INSTANCE_CACHE.get(instanceKey);
        if (inst == null) {
            INSTANCE_LOAD_LOCK.lock();
            try {
                ClassLoader injectorClassLoader = INJECTOR_CLASSLOADER_MAP.get(targetClassLoader);
                if (injectorClassLoader == null) {
                    injectorClassLoader = new LaunchedURLClassLoader(AgentContext.getAgentInfo().getInjectorUrls(), targetClassLoader);
                    INJECTOR_CLASSLOADER_MAP.put(targetClassLoader, injectorClassLoader);
                }
                inst = Class.forName(className, true, injectorClassLoader).newInstance();
                EventListener listener = ((Injector) inst).getListener();
                if (listener != null) {
                    EventContext.INSTANCE.addListener(listener);
                }
            } finally {
                INSTANCE_LOAD_LOCK.unlock();
            }
            INSTANCE_CACHE.put(instanceKey, inst);
        }

        return (T) inst;
    }
}

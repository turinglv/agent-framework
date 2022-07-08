package com.ktd.service.afw.boot.impl.loader;

import com.ktd.service.afw.boot.api.command.EventContext;
import com.ktd.service.afw.boot.api.command.EventListener;
import com.ktd.service.afw.boot.api.config.Configs;
import com.ktd.service.afw.boot.api.core.Brick;
import com.ktd.service.afw.boot.api.exception.BootServiceException;
import com.ktd.service.afw.boot.api.loader.service.ServiceLoadResult;
import com.ktd.service.afw.boot.api.loader.service.ServiceLoaderHelper;
import com.ktd.service.afw.boot.api.log.AgentErrorContext;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.boot.impl.AfwAgentBootstrap;
import com.ktd.service.afw.boot.impl.anno.Component;
import com.ktd.service.afw.core.anno.source.Todo;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Todo("后续需要将IDR+Component结合")
public enum BootServiceContext {

    INSTANCE;

    /**
     * 日志输出
     */
    private static final Log LOG = LogProvider.getBootLog();

    private Map<Class<?>, Brick> brickMap = new HashMap<>();

    public void boot() {
        brickMap = loadBrickMap();
        prepare();
        startup();
        onComplete();
    }

    public void shutdown() {
        LOG.info("应用停止");
        for (Brick service : brickMap.values()) {
            try {
                service.shutdown();
            } catch (Throwable e) {
                LOG.error("BootServiceContext 暂停失败{}", service.getClass().getName(), e);
            }
        }
    }

    private Map<Class<?>, Brick> loadBrickMap() {
        Map<Class<?>, Class<? extends Brick>> brickClassMap = new HashMap<>();
        Collection<String> disableClassSet = Configs.Component.DISABLE_CLASSES.getCollection();
        List<ServiceLoadResult<Brick>> brickSlrs = loadAllBricks();
        for (ServiceLoadResult<Brick> brickSlr : brickSlrs) {
            if (!disableClassSet.isEmpty() && disableClassSet.contains(brickSlr.getClassName())) {
                LOG.info("[BootComponent] 禁用Component:{}", brickSlr.getClassName());
                continue;
            }
            Class<? extends Brick> brickClass = brickSlr.getClazz();
            Component currentAnno = brickClass.getAnnotation(Component.class);
            Class bootServiceClass = currentAnno == null ? brickClass : currentAnno.value();
            Class<?> existServiceClazz = brickClassMap.get(bootServiceClass);
            Component existAnno = existServiceClazz == null ? null : existServiceClazz.getDeclaredAnnotation(Component.class);
            if (existAnno != null && currentAnno != null && existAnno.priority() > currentAnno.priority()) {
                continue;
            }
            LOG.debug("Service Load:{} ;Anno :{}", brickClass, currentAnno);
            brickClassMap.put(bootServiceClass, brickClass);
        }
        Map<Class<?>, Brick> bootServiceMap = new HashMap<>();
        for (Map.Entry<Class<?>, Class<? extends Brick>> entry : brickClassMap.entrySet()) {
            try {
                bootServiceMap.put(entry.getKey(), entry.getValue().newInstance());
            } catch (Exception e) {
                LOG.error("创建BootService发生异常：" + entry.getValue(), e);
                AgentErrorContext.asyncReport(e, "创建BootService发生异常：" + entry.getValue());
                throw new BootServiceException("创建BootService发生异常：" + entry.getValue(), e);
            }
        }
        return bootServiceMap;
    }

    private void prepare() {
        Iterator<Map.Entry<Class<?>, Brick>> iterator = brickMap.entrySet().iterator();
        Map.Entry<Class<?>, Brick> entry;
        for (; iterator.hasNext(); ) {
            entry = iterator.next();
            try {
                entry.getValue().prepare();
            } catch (Throwable e) {
                iterator.remove();
                LOG.error("BootServiceContext try to pre-start [{}] fail.", entry.getValue().getClass().getName(), e);
                AgentErrorContext.asyncReport(new BootServiceException(e), "启动服务失败：" + entry.getValue().getClass().getName());
            }
        }
    }

    private void startup() {
        Iterator<Map.Entry<Class<?>, Brick>> iterator = brickMap.entrySet().iterator();
        Map.Entry<Class<?>, Brick> entry;
        for (; iterator.hasNext(); ) {
            entry = iterator.next();
            try {
                entry.getValue().boot();
            } catch (Throwable e) {
                iterator.remove();
                LOG.error("BootServiceContext try to start [{}] fail.", entry.getValue().getClass().getName(), e);
                AgentErrorContext.asyncReport(new BootServiceException(e), "启动服务失败：" + entry.getValue().getClass().getName());
            }
        }
    }

    private void onComplete() {
        Iterator<Map.Entry<Class<?>, Brick>> iterator = brickMap.entrySet().iterator();
        Map.Entry<Class<?>, Brick> entry;
        for (; iterator.hasNext(); ) {
            entry = iterator.next();
            try {
                Brick brick = entry.getValue();
                brick.onComplete();
                EventListener eventListener = brick.getListener();
                if (eventListener != null) {
                    EventContext.INSTANCE.addListener(eventListener);
                }
            } catch (Throwable e) {
                iterator.remove();
                LOG.error("Service [{}] AfterBoot process fails", entry.getValue().getClass().getName(), e);
                AgentErrorContext.asyncReport(new BootServiceException(e), "启动服务失败：" + entry.getValue().getClass().getName());
            }
        }
    }

    /**
     * Find a {@link Brick} implementation, which is already started.
     *
     * @param serviceClass class name.
     * @param <T>          {@link Brick} implementation class.
     * @return {@link Brick} instance
     */
    public <T> T findService(Class<T> serviceClass) {
        return (T) brickMap.get(serviceClass);
    }

    private List<ServiceLoadResult<Brick>> loadAllBricks() {
        List<ServiceLoadResult<Brick>> allServices = new LinkedList<>();
        List<ServiceLoadResult<Brick>> serviceInterfaces = ServiceLoaderHelper.loadServices(
            AfwAgentBootstrap.getAgentClassLoader(), Brick.class, false, false);
        for (ServiceLoadResult<Brick> interfaceResult : serviceInterfaces) {
            List<ServiceLoadResult<Brick>> bootServices = ServiceLoaderHelper.loadServices(AfwAgentBootstrap.getAgentClassLoader(), interfaceResult.getClassName(), true, false);
            LOG.info("加载BootInterface：{}", interfaceResult.getClassName(), bootServices.size());
            allServices.addAll(bootServices);
        }
        return allServices;
    }

    public void register(Class clazz, Brick brick) {
        if (clazz == null || brick == null) {
            LOG.error("注册的Class:{}和bootService:{}不能null", clazz, brick);
            return;
        }
        if (!clazz.isAssignableFrom(brick.getClass())) {
            LOG.error("注册的bootService:{}不能赋值给Class:{}", brick, clazz);
            return;
        }
        this.brickMap.put(clazz, brick);
    }
}

package com.ktd.service.afw.boot.impl;

import com.ktd.service.afw.boot.api.config.Configs;
import com.ktd.service.afw.boot.api.core.AgentContext;
import com.ktd.service.afw.boot.api.core.AgentCst;
import com.ktd.service.afw.boot.api.injector.classloader.InjectorClassLoader;
import com.ktd.service.afw.boot.api.injector.define.AbstractClassInjectorDefine;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.boot.api.util.AgentLogUtil;
import com.ktd.service.afw.boot.api.util.FinalFieldUtil;
import com.ktd.service.afw.boot.impl.initial.InitializerContext;
import com.ktd.service.afw.boot.impl.injector.InjectorFinder;
import com.ktd.service.afw.boot.impl.loader.BootServiceContext;
import com.ktd.service.afw.boot.impl.log.BootLog;
import com.ktd.service.afw.boot.impl.log.LogLevel;
import com.ktd.service.afw.boot.impl.log.Slf4jLoggerFactory;
import com.ktd.service.afw.core.exception.AgentException;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;
import ktd.bb.agent.builder.AgentBuilder;

public class AfwAgentBootstrap {

    /**
     * 日志输出
     */
    private static final Log LOG = BootLog.getInstance();

    /**
     * Boot加载器
     */
    private static volatile ClassLoader AGENT_CLASS_LOADER = null;

    /**
     * Boot的启动函数
     *
     * @param agentArgs       启动参数
     * @param instrumentation 控制器
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        final InjectorFinder injectorFinder;
        try {
            // 1. 第1个执行：初始化Log
            BootLog.setLogLevel(LogLevel.toInt(Configs.Log.LEVEL.getProp()));
            FinalFieldUtil.setValue(LogProvider.class, AgentCst.INSTANCE_FIELD_NAME, new Slf4jLoggerFactory());

            // 2. 第2个执行
            ClassLoader agentClassLoader = AfwAgentBootstrap.class.getClassLoader();
            if (agentClassLoader == null) {
                agentClassLoader = Thread.currentThread().getContextClassLoader();
                LOG.error("AfwAgentBootstrap加载器获取异常，请使用正确的路径加载AfwAgentBootstrap类。");
            }
            FinalFieldUtil.setValue(AfwAgentBootstrap.class, "AGENT_CLASS_LOADER", agentClassLoader);

            InitializerContext.init();

            InjectorClassLoader injectorClassLoader = new InjectorClassLoader(
                AgentContext.getAgentInfo().getInjectorUrls(), agentClassLoader);
            injectorFinder = new InjectorFinder(loadInjectorDefines(injectorClassLoader));
            BootServiceContext.INSTANCE.boot();
            new AgentBuilder.Default()
                    .type(injectorFinder.buildMatch())
                    .transform(new ClassTransformer(injectorFinder))
                    .with(new ClassTransformListener())
                    .installOn(instrumentation);
        } catch (Exception e) {
            AgentLogUtil.error(" Afw Agent启动失败", e);
            throw new AgentException("AfwAgentBootstrap启动失败。", e);
        }
        // TODO sender 需要开发
        Runtime.getRuntime().addShutdownHook(new Thread(() -> BootServiceContext.INSTANCE.shutdown(), "Afw-Shutdown-Hook"));

    }

    public static ClassLoader getAgentClassLoader() {
        return AGENT_CLASS_LOADER;
    }

    /**
     * 加载全部Injector
     */
    private static List<AbstractClassInjectorDefine> loadInjectorDefines(
        InjectorClassLoader injectorClassLoader) {

        List<AbstractClassInjectorDefine> injectorDefines = new ArrayList<>();
        for (String injectorClazzName : injectorClassLoader.getInjectorDefineClazzNames()) {
            try {
                AbstractClassInjectorDefine injectorDefine = (AbstractClassInjectorDefine) Class.forName(injectorClazzName, true, injectorClassLoader).newInstance();
                injectorDefines.add(injectorDefine);
            } catch (Throwable t) {
                LOG.error("加载Injector失败:{}", injectorClazzName, t);
            }
        }

        return injectorDefines;

    }

}

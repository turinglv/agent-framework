package com.ktd.service.afw.boot.impl.initial;

import com.ktd.service.afw.boot.api.command.EventContext;
import com.ktd.service.afw.boot.api.command.EventPublisher;
import com.ktd.service.afw.boot.api.core.AgentCst;
import com.ktd.service.afw.boot.api.initial.ContextInitializer;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.boot.api.util.FinalFieldUtil;
import com.ktd.service.afw.boot.impl.console.DefaultEventContext;

public final class EventContextInitializer implements ContextInitializer {

    /**
     * 日志输出
     */
    private static final Log LOG = LogProvider.getBootLog();

    @Override
    public void prepare() {
        try {
            DefaultEventContext eventContext = new DefaultEventContext();
            FinalFieldUtil.setValue(EventPublisher.class, AgentCst.INSTANCE_FIELD_NAME, eventContext);
            FinalFieldUtil.setValue(EventContext.class, AgentCst.INSTANCE_FIELD_NAME, eventContext);
        } catch (Exception e) {
            LOG.error(" Event Context Initializer 初始化失败", e);
        }
    }

}

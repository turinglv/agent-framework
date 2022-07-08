package com.ktd.service.afw.boot.api.command;

import static com.ktd.service.afw.boot.api.core.AgentCst.MATCH_ALL;

import com.ktd.service.afw.boot.api.log.AgentErrorContext;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.core.command.Event;
import com.ktd.service.afw.core.model.InstanceInfo;

public interface EventPublisher {

    /**
     * 日志输出
     */
    Log LOG = LogProvider.getBootLog();

    EventPublisher INSTANCE = null;

    static void publish(Event event) {
        if (event == null) {
            return;
        }
        if (INSTANCE == null) {
            LOG.error("[EventPublish] 没有注册EventPublisher。");
            AgentErrorContext.asyncReport(null, "没有注册EventPublisher。");
            return;
        }

        if (!match(event)) {
            LOG.info("[Event] 收到Event，但该Event对本实例不生效，所以忽略:{}", event);
            return;
        }
        INSTANCE.publishEvent(event);
    }

    void publishEvent(Event event);

    static boolean match(Event event) {
        if (event.getApplicationName() == null || (!MATCH_ALL.equals(event.getApplicationName()) && !InstanceInfo.INSTANCE.getApplicationName().equals(event.getApplicationName()))) {
            return false;
        }
        String ipv4 = InstanceInfo.INSTANCE.getIpv4();
        if (event.getNotEffectIps() != null && event.getNotEffectIps().contains(ipv4)) {
            return false;
        }
        return event.getEffectIps() == null || event.getEffectIps().contains(ipv4);

    }
}

package com.ktd.service.afw.boot.impl.initial;

import com.ktd.service.afw.boot.api.core.AgentCst;
import com.ktd.service.afw.boot.api.initial.ContextInitializer;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.boot.api.serialize.Serializer;
import com.ktd.service.afw.boot.api.util.FinalFieldUtil;
import com.ktd.service.afw.boot.impl.serialize.GsonSerializer;

public final class SerializerContextInitializer implements ContextInitializer {

    /**
     * 日志输出
     */
    private static final Log LOG = LogProvider.getBootLog();

    @Override
    public void prepare() {
        try {
            GsonSerializer serializer = new GsonSerializer();
            FinalFieldUtil.setValue(Serializer.class, AgentCst.INSTANCE_FIELD_NAME, serializer);
        } catch (Exception e) {
            LOG.error(" Serializer Context Initializer 初始化失败", e);
        }
    }

}

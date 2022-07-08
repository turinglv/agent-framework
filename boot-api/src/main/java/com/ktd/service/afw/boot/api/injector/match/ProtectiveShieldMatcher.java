package com.ktd.service.afw.boot.api.injector.match;


import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.core.anno.source.FixMe;
import ktd.bb.matcher.ElementMatcher;

@FixMe("匹配速度太慢")
public class ProtectiveShieldMatcher<T> extends ElementMatcher.Junction.AbstractBase<T> {

    private static final Log LOG = LogProvider.getBootLog();

    private final ElementMatcher<? super T> matcher;

    public ProtectiveShieldMatcher(ElementMatcher<? super T> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(T target) {
        boolean matchFlag = false;
        try {
            matchFlag = this.matcher.matches(target);
        } catch (Throwable t) {
            LOG.debug("[Injector.Match]判定是否注入该类{}发生异常.", target, t);
        }
        if (!matchFlag) {
            LOG.debug("[Injector.Match] 不增强该类:{}", target);
        } else {
            LOG.info("[Injector.Match] 增强该类:{}", target);
        }
        return matchFlag;
    }
}

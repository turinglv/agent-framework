package com.ktd.service.afw.core.api;

/**
 * 标识符接口
 *
 */
public interface Idrable {

    /**
     * 获取当前的标识符
     */
    default String getIdr() {
        return this.getClass().getSimpleName();
    }
}

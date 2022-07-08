package com.ktd.service.afw.core.injector;

/**
 * Injector类型
 */
public class InjectorType {

    /**
     * Injector的编号
     */
    private final int id;

    /**
     * Injector的短编码
     */
    private final String shortIdr;

    /**
     * Injector的长编码
     */
    private final String idr;

    public InjectorType(int id, String shortIdr, String idr) {
        this.id = id;
        this.shortIdr = shortIdr;
        this.idr = idr;
    }

    public int getId() {
        return id;
    }

    public String getShortIdr() {
        return shortIdr;
    }

    public String getIdr() {
        return idr;
    }

    @Override
    public String toString() {
        return "InjectorType{" + "id=" + id + ", shortIdr='" + shortIdr + '\'' + ", idr='" + idr + '\'' + '}';
    }
}

package com.ktd.service.afw.agent;

import java.io.PrintStream;

/**
 * InitSystemProperty
 */
public class InitSystemProperty {

    /**
     * 私有构造函数
     */
    private InitSystemProperty() {
        super();
    }

    protected enum Property {

        JMX_MXBEAN_MULTI_NAME("jmx.mxbean.multiname", "true"),
        ;

        protected final String key;

        protected final String value;

        Property(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void init() {
        PrintStream stdOut = System.out;
        for (Property property : Property.values()) {
            if (System.getProperty(property.key) == null) {
                stdOut.println(" AFW AGENT Set Prop    : [ " + property.key + " : " + property.value + " ]");
                System.setProperty(property.key, property.value);
            } else {
                stdOut.println(" AFW AGENT Get Prop    : [ " + property.key + " : " + System.getProperty(property.key) + " ]");
            }
        }
    }
}

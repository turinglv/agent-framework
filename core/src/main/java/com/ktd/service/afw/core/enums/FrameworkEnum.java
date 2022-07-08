package com.ktd.service.afw.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 此处的编码范围尽量和Pinpoint一致
 * InternalUse 0~999
 * Server 1000~1999
 * DB Client 2000~2999
 * Cache Client 8000~8999
 * RPC Client 9000~9999
 * Others 5000~7999
 */
public enum FrameworkEnum {

    /**
     * 未知
     * <p>
     * 自定义的，和Pinpoint不一致
     */
    UNKNOWN(0, "unknown", "UNKNOWN"),
    METRIC(2, "metric", "Metric"),
    METHOD(3, "method", "Method"),

    /**
     * Iris
     * <p>
     * 自定义的，和Pinpoint不一致
     */
    IRIS(901, "iris", "Iris"),

    /**
     * Cat
     * <p>
     * 自定义的，和Pinpoint不一致
     */
    CAT(902, "cat", "CAT"),

    /**
     * Data Tracker
     * <p>
     * 自定义的，和Pinpoint不一致
     */
    DATA_TRACKER(903, "data-tracker", "DataTracker"),


    /**
     * Log4j2
     * <p>
     * 自定义的，和Pinpoint不一致
     */
    LOG4J2(1801, "log4j2", "Log4j2"),

    /**
     * GLB的链路标识信息和上报信息是上报的，不在Agent的监控范围内。
     *
     * @deprecated {@link #LB}
     */
    GLB(1901, "glb", "glb"),

    /**
     * FLB的链路标识信息和上报信息是上报的，不在Agent的监控范围内。
     *
     * @deprecated {@link #LB}
     */
    FLB(1902, "flb", "flb"),

    /**
     * 所有的LB
     */
    LB(1901, "lb", "Load balance"),

    /**
     * TOMCAT
     * <p>
     * 和Pinpoint一致
     */
    TOMCAT(1010, "tomcat", "Tomcat"),

    /**
     * Dubbo的Provider
     * <p>
     * 和Pinpoint一致
     */
    DUBBO_PROVIDER(1110, "dubbo-provider", "Dubbo Provider"),

    /**
     * Thrift Provider
     */
    THRIFT_PROVIDER(1900, "thrift-provider", "Thrift Provider"),

    /**
     * Spring Boot
     * <p>
     * 和Pinpoint一致
     */
    SPRING_BOOT(1210, "spring-boot", "Spring Boot"),

    /**
     * MySQL
     * <p>
     * 和Pinpoint一致
     */
    MYSQL(2100, "mysql", "MySQL"),

    /**
     * HBase
     * <p>
     * 自定义，和Pinpoint不一致
     */
    HBASE(2900, "hbase", "HBase"),

    /**
     * Spring Framework MVC
     * <p>
     * 和Pinpoint一致
     */
    SPRING_MVC(5051, "spring-mvc", "Spring MVC"),

    /**
     * Mybatis
     * <p>
     * 和Pinpoint一致
     */
    MYBATIS(5510, "mybatis", "MyBatis"),

    /**
     * Zebra
     */
    ZEBRA(5520, "zebra", "Zebra"),
    /**
     * Hikari
     * <p>
     * 自定义
     */
    HIKARI(5901, "hikari", "Hikari"),

    /**
     * Http Components
     * <p>
     * 和HttpClient一致
     */
    HTTP_COMPONENTS(9052, "http-components", "HTTP Components"),

    /**
     * Dubbo的Consumer
     * <p>
     * 和Pinpoint一致
     */
    DUBBO_CONSUMER(9110, "dubbo-consumer", "Dubbo Consumer"),

    /**
     * Spring的RestTemplate
     * <p>
     * 和Pinpoint一致
     */
    REST_TEMPLATE(9140, "rest-template", "Rest Template"),

    /**
     * org.asynchttpclient:async-http-client
     * <p>
     * 自定义
     */
    ASYNC_HTTP_CLIENT(9950, "async-http-client", "Async HTTP Client"),

    /**
     * Http Client
     */
    HTTP_CLIENT(9961, "http-client", "HTTP Client"),

    /**
     * Http Core
     */
    HTTP_CLIENT_CORE(9962, "http-core", "HTTP Core"),

    /**
     * Http Core NIO
     */
    HTTP_CLIENT_CORE_NIO(9963, "http-core-nio", "HTTP Core NIO"),

    /**
     * Http Async Client
     */
    HTTP_CLIENT_ASYNC(9964, "http-async", "HTTP Async Client"),

    /**
     * Jedis
     * <p>
     * 和Pinpoint一致
     */
    JEDIS(8200, "jedis", "Jedis"),

    /**
     * RabbitMQ Producer
     */
    RABBITMQ_PRODUCER(8300, "rabbitmq-producer", "RabbitMQ Producer"),

    /**
     * RabbitMQ Consumer
     */
    RABBITMQ_CONSUMER(8301, "rabbitmq-consumer", "RabbitMQ Consumer"),

    /**
     * Kakfa Producer
     */
    KAFKA_PRODUCER(8903, "kafka-producer", "Kafka Producer"),

    /**
     * Kakfa Consumer
     */
    KAFKA_CONSUMER(8904, "kafka-consumer", "Kafka Consumer"),

    /**
     * Tiger Producer
     */
    TIGER_PRODUCER(8913, "tiger-producer", "Tiger Producer"),

    /**
     * Tiger Consumer
     */
    TIGER_CONSUMER(8914, "tiger-consumer", "Tiger Consumer"),

    /**
     * BAT
     * 自定义使用
     * Jedis框架一种
     */
    BAT(8910, "bat", "BAT"),
    ;


    private static final Map<Integer, FrameworkEnum> ID_MAP = new HashMap<>(64);

    static {
        for (FrameworkEnum value : FrameworkEnum.values()) {
            ID_MAP.put(value.code, value);
        }
    }

    /**
     * 类型编码
     */
    public final int code;

    /**
     * 字符型唯一标志
     */
    public final String idr;

    /**
     * 名称
     */
    private final String name;

    /**
     * 公共构造函数
     *
     * @param code 类型码
     * @param idr
     * @param name 框架名称
     */
    FrameworkEnum(int code, String idr, String name) {
        this.code = code;
        this.idr = idr;
        this.name = name;
    }


    public static FrameworkEnum fromCode(Integer code) {
        return ID_MAP.getOrDefault(code, UNKNOWN);
    }
}

package org.springframework.aop;

/**
 * 防止Spring AOP注册出现混乱。
 * 需要之后修改整体injector方式，改为ASM的字节码编辑的方式。
 */
public interface SpringProxy {
}

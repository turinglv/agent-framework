package com.ktd.service.afw.boot.api.initial;

/**
 * ContextInitializer
 */
public interface ContextInitializer {

  default void prepare() {
  }

  default void boot() {
  }

}

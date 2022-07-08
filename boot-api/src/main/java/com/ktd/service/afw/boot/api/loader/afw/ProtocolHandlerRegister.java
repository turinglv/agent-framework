package com.ktd.service.afw.boot.api.loader.afw;

import java.net.URLStreamHandler;

public final class ProtocolHandlerRegister {

  private static final String PROTOCOL_HANDLER = "java.protocol.handler.pkgs";

  private static final String HANDLERS_PACKAGE = "com.ktd.service.afw.boot.api.loader";

  private static volatile Boolean registered = false;

  /**
   * 私有构造函数
   */
  private ProtocolHandlerRegister() {
    super();
  }

  /**
   * Register a {@literal 'java.protocol.handler.pkgs'} property so that a {@link URLStreamHandler}
   * will be located to deal with jar URLs.
   */
  public static void registerUrlProtocolHandler() {
    if (registered) {
      return;
    }
    String handlers = System.getProperty(PROTOCOL_HANDLER, "");
    System.setProperty(PROTOCOL_HANDLER,
        ("".equals(handlers) ? HANDLERS_PACKAGE : handlers + "|" + HANDLERS_PACKAGE));
    registered = true;
  }

}

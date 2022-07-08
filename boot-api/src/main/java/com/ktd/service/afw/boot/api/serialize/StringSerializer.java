package com.ktd.service.afw.boot.api.serialize;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class StringSerializer implements Serializer {

  @Override
  public String toString(Object object) {
    return Objects.toString(object, null);
  }

  @Override
  public byte[] toBytes(Object object) {
    return object == null ? null : object.toString().getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public <T> T parseObject(String json, Class<T> clazz) {
    return null;
  }

  @Override
  public <T> T parseObject(byte[] json, Class<T> clazz) {
    return null;
  }
}

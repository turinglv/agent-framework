package com.ktd.service.afw.boot.api.serialize;

public interface Serializer {

  Serializer INSTANCE = new StringSerializer();

  String toString(Object object);

  byte[] toBytes(Object object);

  <T> T parseObject(String json, Class<T> clazz);

  <T> T parseObject(byte[] json, Class<T> clazz);

}

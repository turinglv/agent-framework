package com.ktd.service.afw.boot.impl.serialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ktd.service.afw.boot.api.serialize.Serializer;
import java.nio.charset.StandardCharsets;

public class GsonSerializer implements Serializer {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    public String toString(Object object) {
        return GSON.toJson(object);
    }

    @Override
    public byte[] toBytes(Object object) {
        return GSON.toJson(object).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T parseObject(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    @Override
    public <T> T parseObject(byte[] json, Class<T> clazz) {
        return GSON.fromJson(new String(json, StandardCharsets.UTF_8), clazz);
    }

}

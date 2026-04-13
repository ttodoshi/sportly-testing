package com.simple.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScenarioContext {

    private final Map<String, Object> data = new ConcurrentHashMap<>();

    public <T> void set(String key, T value) {
        data.put(key, value);
    }

    public <T> T get(String key) {
        return (T) data.get(key);
    }
}
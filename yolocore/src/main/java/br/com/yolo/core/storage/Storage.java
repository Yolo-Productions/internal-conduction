package br.com.yolo.core.storage;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Storage<K, V> {
    protected final Map<K, V> kvMap = new LinkedHashMap<>();

    public void add(K key, V value) {
        if (!kvMap.containsKey(key))
            kvMap.put(key, value);
    }

    public void remove(K key) {
        kvMap.remove(key);
    }

    public V read(K key) {
        return kvMap.getOrDefault(key, null);
    }

    public Collection<V> toList() {
        return kvMap.values();
    }
}

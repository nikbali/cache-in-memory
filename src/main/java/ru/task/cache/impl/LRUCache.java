package ru.task.cache.impl;

import ru.task.cache.api.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Простой LRU-кэш, в котором вытеснение самых старых элементов осуществляется с помощью {@link LinkedHashMap}
 */
public class LRUCache<K, V> implements Cache<K, V>{

    /** Default maximum size */
    private static final int DEFAULT_MAX_SIZE = 100;

    /** Maximum size */
    private final int maxSize;

    private final Map<K, V> lruMap;



    public LRUCache(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Максимальный размер кэша должен быть больше нуля");
        }

        this.maxSize = maxSize;
        this.lruMap = new LinkedHashMap<K, V>(this.maxSize, 0.75f, true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > LRUCache.this.maxSize;
            }
        };
    }

    public LRUCache() {
        this(DEFAULT_MAX_SIZE);
    }


    @Override
    public V get(K key) {
        return lruMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        return lruMap.put(key, value);
    }

    @Override
    public int getMaxSize() {
        return this.maxSize;
    }

    @Override
    public int getCurrentSize() {
        return this.lruMap.size();
    }

    @Override
    public Set<K> keySet() {
        return lruMap.keySet();
    }

}

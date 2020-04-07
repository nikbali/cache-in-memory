package ru.task.cache.impl;


import ru.task.cache.api.Cache;
import ru.task.cache.api.CacheManager;
import ru.task.cache.api.Configuration;
import ru.task.cache.api.EvictionStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Управление кэшами в приложении
 */
public class CommonCacheManager implements CacheManager {

    /**
     * Класс является Singleton
     */
    private static CommonCacheManager instance;

    /**
     * Мапа со всеми кэшами в системе
     */
    private final Map<String, Cache> cacheMap;

    private CommonCacheManager() {
        this.cacheMap = new HashMap<>();
    }

    @Override
    public <K, V> Cache<K, V> createCache(String name, Configuration<K, V> config) {

        Cache<K, V> cache;
        switch (config.getEvictionStrategy()) {
            case LFU:
                cache = new LFUCache<>(config.getMaxSize());
                break;
            case LRU:
                cache = new LRUCache<>(config.getMaxSize());
                break;
            default:
                throw new RuntimeException("Не поддерживаемый тип стратегии выталкивания из кэша");
        }

        cacheMap.put(name, cache);
        return cache;
    }

    @Override
    public Cache getCacheByName(String name) {
        return cacheMap.get(name);
    }


    @Override
    public void removeCache(String name) {
        cacheMap.remove(name);
    }

    public static CommonCacheManager getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (CommonCacheManager.class) {
                if (Objects.isNull(instance)) {
                    instance = new CommonCacheManager();
                }
            }
        }
        return instance;
    }
}

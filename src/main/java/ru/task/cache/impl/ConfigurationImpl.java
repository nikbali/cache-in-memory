package ru.task.cache.impl;

import ru.task.cache.api.Cache;
import ru.task.cache.api.Configuration;
import ru.task.cache.api.EvictionStrategy;

/**
 * Конфигурация для создания объектов {@link Cache}
 *
 * @param <K> тип ключей
 * @param <V> тип объектов в кэше
 */
public class ConfigurationImpl<K, V> implements Configuration<K, V> {

    /**
     * Тип ключей
     */
    private Class<K> keyType;

    /**
     * Тип элементов
     */
    private Class<V> valueType;

    /**
     * СТратегия выталкивания
     */
    private EvictionStrategy evictionStrategy;

    /**
     * Максимальный рамер кэша
     */
    private int maxSize;

    public ConfigurationImpl(Class<K> keyType, Class<V> valueType, EvictionStrategy evictionStrategy, int maxSize){
        this.keyType = keyType;
        this.valueType = valueType;

        this.evictionStrategy = evictionStrategy;
        this.maxSize = maxSize;
    }

    @Override
    public Class<K> getKeyType() {
        return this.keyType;
    }

    @Override
    public Class<V> getValueType() {
        return this.valueType;
    }

    @Override
    public EvictionStrategy getEvictionStrategy() {
        return this.evictionStrategy;
    }

    @Override
    public int getMaxSize() {
        return this.maxSize;
    }
}

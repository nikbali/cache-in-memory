package ru.task.cache.api;

import java.util.Set;

/**
 * Общие методы работы с кэшем
 * @param <K> тип ключа
 * @param <V> тип объектов
 */
public interface Cache<K, V> {

    /** Default maximum size */
    int DEFAULT_MAX_SIZE = 100;

    /**
     * Получить значение по ключу
     * @param key ключ
     * @return значение
     */
    V get(K key);

    /**
     * Добавить в кэш
     *
     */
    V put(K key, V value);

    /**
     * Получить максимальный размер кэша
     */
    int getMaxSize();

    /**
     * Получить текущий размер кэша
     */
    int getCurrentSize();

    /**
     * Множество всех ключей в кэше
     */
    Set<K> keySet();
}

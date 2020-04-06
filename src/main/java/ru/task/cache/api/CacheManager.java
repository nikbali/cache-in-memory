package ru.task.cache.api;


/**
 * Управляет кэшем {@link }
 */
public interface CacheManager{

    /**
     * Создает {@link Cache}.
     *
     * @param name название кэша
     * @param config конфигруация
     */
    <K, V> Cache createCache(String name, Configuration<K, V> config);


    /**
     *  Возвращает кэш по его названию
     */
    Cache getCacheByName(String name);

    /**
     * Удаляет кэш
     */
    void removeCache(String name);

}

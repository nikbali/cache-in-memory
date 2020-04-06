package ru.task.cache.api;

/**
 * Конфигурация для создания объектов {@link Cache}
 * @param <K> тип ключей
 * @param <V> тип объектов в кэше
 */
public interface Configuration<K, V> {


    /**
     * Тип ключей по которому хранятся данные в кэше
     */
    Class<K> getKeyType();

    /**
     * Тип объектов, хранимых в кэше
     */
    Class<V> getValueType();

    /**
     * Стратегия вытеснения данных
     * @return стратегия
     */
    EvictionStrategy getEvictionStrategy();



    /**
     * Возвращает размер кэша
     */
    int getMaxSize();
}

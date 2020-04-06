package ru.task.cache.api;

/**
 * Стратегия вытеснения данных из кэша
 */
public enum EvictionStrategy {

    /**
     * Вытеснение самых старых элементов
     */
    LRU,

    /**
     * Least-Frequently Used (Наименее часто используемый)
     */
    LFU
}

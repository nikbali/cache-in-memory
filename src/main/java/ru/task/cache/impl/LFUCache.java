package ru.task.cache.impl;

import ru.task.cache.api.Cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Простой LFU-кэш, в котором вытеснение релизовано с учетом количества обращений(частоты) к элементу по ключу
 */
public class LFUCache<K, V> implements Cache<K, V> {

    /**
     * Максимальный размер
     */
    private final int maxSize;

    private final LinkedHashMap<K, ValueWithFrequency> lfuMap;


    public LFUCache(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Максимальный размер кэша должен быть больше нуля");
        }
        this.maxSize = maxSize;
        this.lfuMap = new LinkedHashMap<>(this.maxSize, 0.75f);
    }

    @Override
    public V get(K key) {

        ValueWithFrequency valueWithFrequency = lfuMap.get(key);

        if (valueWithFrequency == null) {
            return null;
        }

        valueWithFrequency.incrementFrequency();

        return valueWithFrequency.getValue();
    }

    @Override
    public V put(K key, V value) {

        if (lfuMap.size() >= maxSize) {

            long min = Long.MAX_VALUE;
            K keyForRemove = null;

            for (Map.Entry<K, ValueWithFrequency> entry : lfuMap.entrySet()) {

                //элемент с таким ключем уже есть
                if (entry.getKey().equals(key)) {
                    return putSafe(key, value);
                }

                if (min >= entry.getValue().getFrequency()) {
                    min = entry.getValue().getFrequency();
                    keyForRemove = entry.getKey();
                }
            }

            //удаляем найденный элемент
            lfuMap.remove(keyForRemove);
        }

        return putSafe(key, value);
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public int getCurrentSize() {
        return lfuMap.size();
    }

    @Override
    public Set<K> keySet() {
        return lfuMap.keySet();
    }


    /* Метод добавялет в мапку */
    private V putSafe(K key, V value) {
        ValueWithFrequency oldElement = lfuMap.put(key, new ValueWithFrequency(value));
        if (oldElement == null) {
            return null;
        }
        return oldElement.getValue();
    }

    /**
     * Обертка в которой храним элементы кэша с частотами
     */
    private class ValueWithFrequency {
        /**
         * Элемент
         */
        private final V value;

        /**
         * Количество обращений к элементу
         */
        private long frequency;

        public ValueWithFrequency(V value) {
            this.value = value;
            this.frequency = 1;
        }

        public V getValue() {
            return value;
        }

        public long getFrequency() {
            return frequency;
        }

        void incrementFrequency() {
            frequency++;
        }
    }
}

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;
import ru.task.cache.api.Cache;
import ru.task.cache.api.CacheManager;
import ru.task.cache.api.Configuration;
import ru.task.cache.api.EvictionStrategy;
import ru.task.cache.impl.CommonCacheManager;
import ru.task.cache.impl.ConfigurationImpl;

import java.math.BigDecimal;

/**
 * Примеры работы с {@link CacheManager}
 * и корректности работы {@link ru.task.cache.impl.LRUCache} и {@link ru.task.cache.impl.LFUCache}
 */
public class CacheManagerTest {

    @Test
    public void exampleWorkingLRUCache() {

        int MAX_SIZE = 3;

        /* Создадим LRU-кэш город - население */
        Configuration<String, Long> cityToPopulationCacheConfiguration = new ConfigurationImpl<>(
                String.class,
                Long.class,
                EvictionStrategy.LRU,
                MAX_SIZE);

        CacheManager cacheManager = CommonCacheManager.getInstance();

        //создаем кэш с заданной конфигурацией
        cacheManager.createCache("cities", cityToPopulationCacheConfiguration);

        Cache cache = cacheManager.getCacheByName("cities");

        cache.put("Moscow", 12_000_000L);
        cache.put("Tokyo", 20_000_000L);
        cache.put("New York", 13_000_000L);
        cache.put("London", 7_000_000L);
        cache.put("Rome", 3_000_000L);
        cache.put("Boston", 700_000L);
        cache.put("San Diego", 600_000L);

        Assert.assertEquals(700_000L, cache.get("Boston"));
        Assert.assertEquals(3_000_000L, cache.get("Rome"));
        Assert.assertNull(cache.get("Moscow"));

        //в кэше ожидаем увидеть последние 3 добавленных записи
        Assert.assertEquals(
                Sets.newHashSet("Rome", "Boston", "San Diego")
                , cache.keySet());

    }

    @Test
    public void exampleWorkingLFUCache() {

        int MAX_SIZE = 3;

        /* Создадим LFU-кэш счет клиента - баланс */
        Configuration<String, BigDecimal> accountToBalanceCacheConfiguration = new ConfigurationImpl<>(
                String.class,
                BigDecimal.class,
                EvictionStrategy.LFU,
                MAX_SIZE);

        CacheManager cacheManager = CommonCacheManager.getInstance();

        //создаем кэш с заданной конфигурацией
        cacheManager.createCache("accounts", accountToBalanceCacheConfiguration);

        //тестовые данные
        Cache cache = cacheManager.getCacheByName("accounts");
        cache.put("1122", BigDecimal.valueOf(100000));
        cache.put("1123", BigDecimal.valueOf(400000));
        cache.put("1124", BigDecimal.valueOf(500000));


        //обращения к кэшу
        cache.get("1122");
        cache.get("1123");
        cache.get("1124");
        cache.get("1122");
        cache.get("1124");

        //при добавлении нового элемента, из кэша выкенет элемент по ключу -> 1123
        cache.put("1125", BigDecimal.valueOf(900000));

        Assert.assertEquals(
                Sets.newHashSet("1122", "1124", "1125")
                , cache.keySet());

        Assert.assertEquals(BigDecimal.valueOf(100000), cache.get("1122"));
        Assert.assertEquals(BigDecimal.valueOf(500000), cache.get("1124"));
        Assert.assertEquals(BigDecimal.valueOf(900000), cache.get("1125"));


    }
}

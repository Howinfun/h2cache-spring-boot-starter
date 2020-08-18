package com.hyf.cache.manager;

import com.hyf.cache.cachetemplate.H2CacheCache;
import lombok.Data;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * custom cacheManager
 *
 * @author Howinfun
 * @since 2020-04-01
 */
@Data
public class H2CacheManager implements CacheManager {
    private EhCacheCacheManager ehCacheCacheManager;
    private RedisCacheManager redisCacheManager;
    private StringRedisTemplate stringRedisTemplate;
    private Map<String, H2CacheCache> h2CacheCacheMap = new ConcurrentHashMap<>();

    @Override
    public Cache getCache(String name) {
        return h2CacheCacheMap.computeIfAbsent(name, (key) -> {
            return new H2CacheCache(ehCacheCacheManager, redisCacheManager,stringRedisTemplate, name);
        });
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.emptyList();
    }
}

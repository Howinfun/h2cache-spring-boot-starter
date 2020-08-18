package com.hyf.cache.manager;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Custom CacheManager Config
 * @author Howinfun
 * @since 2020-04-01
 */
@Configuration
@ConditionalOnProperty(prefix = "h2cache", value = "enabled", havingValue = "true")
public class H2CacheManagerConfiguration {

    @Bean
    @Primary
    public CacheManager cacheManager(RedisCacheManager redisCacheManager, EhCacheCacheManager ehCacheCacheManager, StringRedisTemplate stringRedisTemplate){
        H2CacheManager h2CacheManager = new H2CacheManager();
        h2CacheManager.setRedisCacheManager(redisCacheManager);
        h2CacheManager.setEhCacheCacheManager(ehCacheCacheManager);
        h2CacheManager.setStringRedisTemplate(stringRedisTemplate);
        return h2CacheManager;
    }
}

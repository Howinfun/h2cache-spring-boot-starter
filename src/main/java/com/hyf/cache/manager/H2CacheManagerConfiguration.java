package com.hyf.cache.manager;

import com.hyf.cache.cachetemplate.H2CacheCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;

/**
 * Custom CacheManager Config
 * @author Howinfun
 * @since 2020-04-01
 */
@Configuration
@ConditionalOnProperty(prefix = "h2cache", value = "enabled", havingValue = "true")
public class H2CacheManagerConfiguration {

    @Bean
    public H2CacheCache h2CacheCache(RedisCacheManager redisCacheManager, EhCacheCacheManager ehCacheCacheManager){

        H2CacheCache h2CacheCache = new H2CacheCache();
        h2CacheCache.setRedisCacheManager(redisCacheManager);
        h2CacheCache.setEhCacheCacheManager(ehCacheCacheManager);
        return h2CacheCache;
    }

    @Bean
    @ConditionalOnBean(H2CacheCache.class)
    @Primary
    public CacheManager cacheManager(H2CacheCache h2CacheCache){
        H2CacheManager h2CacheManager = new H2CacheManager();
        h2CacheManager.setH2CacheCache(h2CacheCache);
        return h2CacheManager;
    }
}

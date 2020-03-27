package com.hyf.cache.manager;

import com.hyf.cache.cachetemplate.H2CacheTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/27
 */
@Configuration
@ConditionalOnProperty(prefix = "h2cache.service", value = "enabled", havingValue = "true")
public class H2CacheManagerConfiguration {

    @Bean
    @ConditionalOnBean({RedisCacheManager.class, EhCacheCacheManager.class})
    public H2CacheTemplate myCacheTemplate(RedisCacheManager redisCacheManager, EhCacheCacheManager ehCacheCacheManager){
        H2CacheTemplate h2CacheTemplate = new H2CacheTemplate();
        h2CacheTemplate.setRedisCacheManager(redisCacheManager);
        h2CacheTemplate.setEhCacheCacheManager(ehCacheCacheManager);
        return h2CacheTemplate;
    }

    @Bean
    @ConditionalOnBean(H2CacheTemplate.class)
    @Primary
    public CacheManager cacheManager(H2CacheTemplate h2CacheTemplate){
        H2CacheManager h2CacheManager = new H2CacheManager();
        h2CacheManager.setH2CacheTemplate(h2CacheTemplate);
        return h2CacheManager;
    }
}

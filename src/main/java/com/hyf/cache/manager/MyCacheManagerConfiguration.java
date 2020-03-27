package com.hyf.cache.manager;

import com.hyf.cache.cachetemplate.MyCacheTemplate;
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
public class MyCacheManagerConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "h2cache.service", value = "enabled", havingValue = "true")
    @ConditionalOnBean({RedisCacheManager.class, EhCacheCacheManager.class})
    public MyCacheTemplate myCacheTemplate(RedisCacheManager redisCacheManager, EhCacheCacheManager ehCacheCacheManager){
        MyCacheTemplate myCacheTemplate = new MyCacheTemplate();
        myCacheTemplate.setRedisCacheManager(redisCacheManager);
        myCacheTemplate.setEhCacheCacheManager(ehCacheCacheManager);
        return myCacheTemplate;
    }

    @Bean
    @ConditionalOnProperty(prefix = "h2cache.service", value = "enabled", havingValue = "true")
    @ConditionalOnBean(MyCacheTemplate.class)
    @Primary
    public CacheManager cacheManager(MyCacheTemplate myCacheTemplate){
        MyCacheManager myCacheManager = new MyCacheManager();
        myCacheManager.setMyCacheTemplate(myCacheTemplate);
        return myCacheManager;
    }
}

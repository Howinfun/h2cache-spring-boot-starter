package com.hyf.cache.ehcache;

import com.hyf.cache.properties.H2CacheEhCacheProperties;
import net.sf.ehcache.Ehcache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Ehcache config
 * @author Howinfun
 * @since 2020-04-01
 */
@Configuration
@ConditionalOnClass(Ehcache.class)
@ConditionalOnProperty(prefix = "h2cache", value = "enabled", havingValue = "true")
public class H2CacheEhCacheConfiguration {

    private final static String CACHE_MANAGER_NAME = "H2CacheEhCacheManager";

    @javax.annotation.Resource
    private H2CacheEhCacheProperties properties;

    @Bean
    @ConditionalOnMissingBean(EhCacheManagerFactoryBean.class)
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new
                EhCacheManagerFactoryBean();
        Resource resource = new ClassPathResource(properties.getFilePath());
        cacheManagerFactoryBean.setConfigLocation(resource);
        cacheManagerFactoryBean.setShared(properties.getShared());
        cacheManagerFactoryBean.setCacheManagerName(CACHE_MANAGER_NAME);
        return cacheManagerFactoryBean;
    }

    @Bean
    @ConditionalOnMissingBean(EhCacheCacheManager.class)
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean){
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager(ehCacheManagerFactoryBean.getObject());
        return ehCacheCacheManager;
    }
}

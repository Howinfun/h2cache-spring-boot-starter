package com.hyf.cache.ehcache;

import com.hyf.cache.properties.H2CacheEhCacheProperties;
import net.sf.ehcache.Ehcache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/25
 */
@Configuration
@ConditionalOnClass(Ehcache.class)
@ConditionalOnProperty(prefix = "h2cache.service", value = "enabled", havingValue = "true")
public class H2CacheEhCacheConfiguration {

    @javax.annotation.Resource
    private H2CacheEhCacheProperties properties;

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new
                EhCacheManagerFactoryBean();
        Resource resource = new ClassPathResource(properties.getFilePath());
        cacheManagerFactoryBean.setConfigLocation(resource);
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }

    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean){
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager(ehCacheManagerFactoryBean.getObject());
        return ehCacheCacheManager;
    }
}

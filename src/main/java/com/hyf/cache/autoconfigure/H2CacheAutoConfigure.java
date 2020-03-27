package com.hyf.cache.autoconfigure;

import com.hyf.cache.ehcache.EhCacheConfiguration;
import com.hyf.cache.manager.MyCacheManagerConfiguration;
import com.hyf.cache.properties.H2CacheEhCacheProperties;
import com.hyf.cache.properties.H2CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/27
 */
@Configuration
@EnableCaching
@Import({EhCacheConfiguration.class, MyCacheManagerConfiguration.class})
@EnableConfigurationProperties({H2CacheEhCacheProperties.class, H2CacheProperties.class})
public class H2CacheAutoConfigure {

}

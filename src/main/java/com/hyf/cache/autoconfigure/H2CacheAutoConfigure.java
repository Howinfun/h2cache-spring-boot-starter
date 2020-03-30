package com.hyf.cache.autoconfigure;

import com.hyf.cache.ehcache.H2CacheEhCacheConfiguration;
import com.hyf.cache.manager.H2CacheManagerConfiguration;
import com.hyf.cache.properties.H2CacheEhCacheProperties;
import com.hyf.cache.properties.H2CacheRedisProperties;
import com.hyf.cache.redis.H2CacheRedisConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Howinfun
 * @desc H2Cache Auto Configure (spring.factories)
 * @date 2020/3/27
 * @email 876237770@qq.com
 */
@Configuration
@EnableCaching
@Import({H2CacheEhCacheConfiguration.class, H2CacheManagerConfiguration.class, H2CacheRedisConfiguration.class})
@EnableConfigurationProperties({H2CacheEhCacheProperties.class, H2CacheRedisProperties.class})
public class H2CacheAutoConfigure {

}

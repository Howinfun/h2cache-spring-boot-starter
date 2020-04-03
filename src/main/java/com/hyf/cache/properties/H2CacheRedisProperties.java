package com.hyf.cache.properties;

import com.hyf.cache.pojo.H2CacheRedisConfig;
import com.hyf.cache.pojo.H2CacheRedisDefault;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Redis Properties
 * @author Howinfun
 * @since 2020-04-01
 */
@Data
@ConfigurationProperties("h2cache.redis")
@ConditionalOnProperty(prefix = "h2cache", value = "enabled", havingValue = "true")
public class H2CacheRedisProperties {

    private H2CacheRedisDefault defaultConfig;
    private List<H2CacheRedisConfig> configList;
}

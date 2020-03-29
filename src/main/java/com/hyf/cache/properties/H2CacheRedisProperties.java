package com.hyf.cache.properties;

import com.hyf.cache.polo.H2CacheRedisConfig;
import com.hyf.cache.polo.H2CacheRedisDefault;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/27
 */
@Data
@ConfigurationProperties("h2cache.service.redis")
public class H2CacheRedisProperties {

    private H2CacheRedisDefault defaultConfig;
    private List<H2CacheRedisConfig> configList;
}

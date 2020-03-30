package com.hyf.cache.properties;

import com.hyf.cache.polo.H2CacheRedisConfig;
import com.hyf.cache.polo.H2CacheRedisDefault;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/27
 * @email 876237770@qq.com
 */
@Data
@ConfigurationProperties("h2cache.service.redis")
@ConditionalOnProperty(prefix = "h2cache.service", value = "enabled", havingValue = "true")
public class H2CacheRedisProperties {

    private H2CacheRedisDefault defaultConfig;
    private List<H2CacheRedisConfig> configList;
}

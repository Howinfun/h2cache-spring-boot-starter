package com.hyf.cache.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Ehcache properties
 * @author Howinfun
 * @since 2020-04-01
 */
@Data
@ConfigurationProperties("h2cache.ehcache")
@ConditionalOnProperty(prefix = "h2cache", value = "enabled", havingValue = "true")
public class H2CacheEhCacheProperties {

    private String filePath;
    private Boolean shared = false;
}

package com.hyf.cache.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/27
 * @email 876237770@qq.com
 */
@Data
@ConfigurationProperties("h2cache.ehcache")
@ConditionalOnProperty(prefix = "h2cache", value = "enabled", havingValue = "true")
public class H2CacheEhCacheProperties {

    private String filePath;
}

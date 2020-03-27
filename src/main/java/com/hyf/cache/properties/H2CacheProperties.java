package com.hyf.cache.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/27
 */
@Data
@ConfigurationProperties("h2cache.service")
public class H2CacheProperties {

    private boolean enabled;
}

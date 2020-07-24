package com.hyf.cache.pojo;

import lombok.Data;

import java.time.Duration;

/**
 * Redis default config
 * @author Howinfun
 * @since 2020-04-01
 */
@Data
public class H2CacheRedisDefault {

    private Duration ttl;
    private Boolean usePrefix = true;
    private Boolean disableNullValues = false;
}

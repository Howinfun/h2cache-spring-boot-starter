package com.hyf.cache.pojo;

import lombok.Data;

/**
 * Redis Config
 * @author howinfun
 * @since 2020-04-01
 */
@Data
public class H2CacheRedisConfig {

    private String cacheName;
    private Integer ttl;
    private Boolean usePrefix = true;
    private Boolean disableNullValues = false;
}

package com.hyf.cache.pojo;

import lombok.Data;

/**
 * Redis default config
 * @author Howinfun
 * @since 2020-04-01
 */
@Data
public class H2CacheRedisDefault {

    private Integer ttl;
    private Boolean usePrefix = true;
    private Boolean disableNullValues = false;
}

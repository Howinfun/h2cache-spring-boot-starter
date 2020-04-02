package com.hyf.cache.pojo;

import lombok.Data;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2020/3/29
 * @email 876237770@qq.com
 */
@Data
public class H2CacheRedisConfig {

    private String cacheName;
    private Integer ttl;
    private Boolean usePrefix = true;
    private Boolean disableNullValues = false;
}
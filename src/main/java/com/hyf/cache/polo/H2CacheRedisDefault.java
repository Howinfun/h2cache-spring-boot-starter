package com.hyf.cache.polo;

import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/27
 */
@Data
public class H2CacheRedisDefault {

    private Integer ttl;
    private Boolean disableNullValues = false;
}

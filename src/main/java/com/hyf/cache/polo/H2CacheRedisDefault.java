package com.hyf.cache.polo;

import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/27
 * @email 876237770@qq.com
 */
@Data
public class H2CacheRedisDefault {

    private Integer ttl;
    private Boolean usePrefix = true;
    private Boolean disableNullValues = false;
}

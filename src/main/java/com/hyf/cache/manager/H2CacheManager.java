package com.hyf.cache.manager;

import com.hyf.cache.cachetemplate.H2CacheCache;
import lombok.Data;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Howinfun
 * @desc H2CacheManager -> Control which cacheName of L2 cache to use
 * @date 2020/3/25
 * @email 876237770@qq.com
 */
@Data
public class H2CacheManager implements CacheManager {

    private H2CacheCache h2CacheCache;

    @Override
    public Cache getCache(String name) {
        this.h2CacheCache.setName(name);
        return this.h2CacheCache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.emptyList();
    }
}

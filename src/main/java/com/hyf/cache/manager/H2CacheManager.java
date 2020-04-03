package com.hyf.cache.manager;

import com.hyf.cache.cachetemplate.H2CacheCache;
import lombok.Data;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Collections;

/**
 * custom cacheManager
 * @author Howinfun
 * @since 2020-04-01
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

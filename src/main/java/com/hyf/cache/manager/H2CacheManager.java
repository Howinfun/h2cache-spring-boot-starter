package com.hyf.cache.manager;

import com.hyf.cache.cachetemplate.H2CacheTemplate;
import lombok.Data;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/25
 */
@Data
public class H2CacheManager implements CacheManager {

    private H2CacheTemplate h2CacheTemplate;

    @Override
    public Cache getCache(String name) {
        this.h2CacheTemplate.setName(name);
        return this.h2CacheTemplate;
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.emptyList();
    }
}

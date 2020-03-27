package com.hyf.cache.cachetemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.concurrent.Callable;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/25
 */
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class H2CacheTemplate implements Cache {

    private EhCacheCacheManager ehCacheCacheManager;
    private RedisCacheManager redisCacheManager;
    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return null;
    }

    @Override
    public ValueWrapper get(Object key) {

        Cache ehCache = ehCacheCacheManager.getCache(this.name);
        if(null != ehCache && null != ehCache.get(key)){
            log.info("取数据 ehcache 库===key:{}",key);
            return ehCache.get(key);
        }

        Cache redisCache = redisCacheManager.getCache(this.name);
        if(null != redisCache && null != redisCache.get(key)){
            log.info("取数据 redis 库===key:{}",key);
            // 将数据存入到 ehcache
            ehCache.put(key,redisCache.get(key).get());
            return redisCache.get(key);
        }

        return null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {

        Cache ehCache = ehCacheCacheManager.getCache(this.name);
        if (null != ehCache){
            log.info("插入 ehcache 库===key:{},value:{}",key,value);
            ehCache.put(key,value);
        }

        Cache redisCache = redisCacheManager.getCache(this.name);
        if (null != redisCache){
            log.info("插入 redis 库===key:{},value:{}",key,value);
            redisCache.put(key,value);
        }

    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public void evict(Object key) {

        Cache ehCache = ehCacheCacheManager.getCache(this.name);
        if (null != ehCache) {
            log.info("删除 ehcache 库===key:{}", key);
            ehCache.evict(key);
        }

        Cache redisCache = redisCacheManager.getCache(this.name);
        if (null != redisCache) {
            log.info("删除 redis 库===key:{}", key);
            redisCache.evict(key);
        }
    }

    @Override
    public void clear() {
        Cache ehCache = ehCacheCacheManager.getCache(this.name);
        if (null != ehCache) {
            log.info("清空 ehcache 库");
            ehCache.clear();
        }

        Cache redisCache = redisCacheManager.getCache(this.name);
        if (null != redisCache) {
            log.info("清空 redis 库");
            redisCache.clear();
        }
    }
}

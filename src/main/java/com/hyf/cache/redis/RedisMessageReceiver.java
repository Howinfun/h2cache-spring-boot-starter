package com.hyf.cache.redis;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

@Data
@Slf4j
public class RedisMessageReceiver {

    private EhCacheCacheManager ehCacheCacheManager;

    public void cacheEvictMessage(String message) {
        log.info("redis订阅者接受消息:{},清除缓存",message);
        JSONObject json = JSONObject.parseObject(message);
        String name = json.getString("name");
        if(name != null && !"".equals(name)){
            Cache ehCache = ehCacheCacheManager.getCache(name);
            if (null != ehCache) {
                Object key = json.get("key");
                if(null != key){
                    log.info("清除缓存,name:[{}],key:[{}]",name,key);
                    ehCache.evict(key);
                }else{
                    log.info("清除缓存,name:[{}]",name);
                    ehCache.clear();
                }
            }
        }
    }
}

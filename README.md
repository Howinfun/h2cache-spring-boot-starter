## What is h2cache-spring-boot-starter?

Use spring-cache, and then integrate local cache [ehcache] and distributed cache [redis] to make secondary cache.

## Getting started

### Add h2cache-spring-boot-starter Maven dependency
```xml
<denpency>
    <groupId>com.github.howinfun</groupId>
    <artifactId>h2cache-spring-boot-starter</artifactId>
    <version>0.0.1</version>
</denpency>
```

### Spring Boot properties
```properties
# Enable L2 cache or not
h2cache.enabled=true

# Ehcache Config
## the path of ehcache.xml (We can put it directly under Resources) 
h2cache.ehcache.filePath=ehcache.xml
#Set whether the EhCache CacheManager should be shared (as a singleton at the ClassLoader level) or independent (typically local within the application).Default is "false", creating an independent local instance.
h2cache.ehcache.shared=true


# Redis Config
## default Config (expire)
h2cache.redis.default-config.ttl=200
### Disable caching {@literal null} values.Default is "false"
h2cache.redis.default-config.disable-null-values=true
### Disable using cache key prefixes.Default is "true"
h2cache.redis.default-config.use-prefix=true
## Custom Config list
### cacheName -> @CacheConfig#cacheNames @Cacheable#cacheNames and other comments, etc                                                            
h2cache.redis.config-list[0].cache-name=userCache
### expire
h2cache.redis.config-list[0].ttl=60
### Disable caching {@literal null} values.Default is "false"
h2cache.redis.config-list[0].use-prefix=true
### Disable using cache key prefixes.Default is "true"
h2cache.redis.config-list[0].disable-null-values=true
h2cache.redis.config-list[1].cache-name=bookCache
h2cache.redis.config-list[1].ttl=60
h2cache.redis.config-list[1].use-prefix=true
```

### The use of cache annotations
We can still use the annotation of spring cache as before~
#### for example：
In the persistence layer, I use [mybatis-plus](https://github.com/baomidou/mybatis-plus#links).
```java
package com.hyf.testDemo.redis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/25
 */
@Repository
// Global cache config,We usually set the cacheName               
@CacheConfig(cacheNames = {"userCache"})
public interface UserMapper extends BaseMapper<User> {

    /**
    * put the data to cache(Ehcache & Redis)
    * @param id
    * @return 
    */
    @Cacheable(key = "#id",unless = "#result == null")
    User selectById(Long id);

    /**
    * put the data to cache After method execution
    * @param user
    * @return 
    */
    @CachePut(key = "#user.id", condition = "#user.name != null and #user.name != ''")
    default User insert0(User user) {
        
        this.insert(user);
        return user;
    }

    /**
    * evict the data from cache
    * @param id
    * @return 
    */
    @CacheEvict(key = "#id")
    int deleteById(Long id);

    /**
    * Using cache annotations in combination
    * @param user
    * @return 
    */
    @Caching(
            evict = {@CacheEvict(key = "#user.id", beforeInvocation = true)},
            put = {@CachePut(key = "#user.id")}
    )
    default User updateUser0(User user){
        
        this.updateById(user);
        return user;
    }
}
```
package com.hyf.cache.redis;

import com.hyf.cache.exception.H2CacheRedisException;
import com.hyf.cache.polo.H2CacheRedisConfig;
import com.hyf.cache.polo.H2CacheRedisDefault;
import com.hyf.cache.properties.H2CacheRedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2020/3/29
 * @email 876237770@qq.com
 */
@Configuration
@ConditionalOnClass(RedisCache.class)
@ConditionalOnProperty(prefix = "h2cache.service", value = "enabled", havingValue = "true")
@Slf4j
public class H2CacheRedisConfiguratin {

    @Resource
    private H2CacheRedisProperties properties;

    @Bean
    @ConditionalOnMissingBean(RedisCacheManager.class)
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) throws H2CacheRedisException {

        try {

            RedisCacheManager redisCacheManager;
            H2CacheRedisDefault redisDefault = properties.getDefaultConfig();
            RedisCacheConfiguration defaultConfig;
            if (null != redisDefault){
                if (false == redisDefault.getDisableNullValues()){
                    defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                            .entryTtl(Duration.ofSeconds(redisDefault.getTtl()))
                            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()));
                }else {
                    defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                            .entryTtl(Duration.ofSeconds(redisDefault.getTtl()))
                            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                            .disableCachingNullValues();
                }
            }else {
                defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                        .disableCachingNullValues();
            }

            List<H2CacheRedisConfig> configList = properties.getConfigList();
            Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>(configList.size());

            if (null != configList && configList.size() > 0){

                for (H2CacheRedisConfig h2CacheRedisConfig : configList) {
                    RedisCacheConfiguration tempConfig;
                    if (null != h2CacheRedisConfig.getCacheName() && !"".equals(h2CacheRedisConfig.getCacheName())){
                        if (h2CacheRedisConfig.getDisableNullValues()){
                            tempConfig = RedisCacheConfiguration.defaultCacheConfig()
                                    .entryTtl(Duration.ofSeconds(h2CacheRedisConfig.getTtl()))
                                    .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                                    .disableCachingNullValues();
                        }else {
                            tempConfig = RedisCacheConfiguration.defaultCacheConfig()
                                    .entryTtl(Duration.ofSeconds(h2CacheRedisConfig.getTtl()))
                                    .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                                    .disableCachingNullValues();
                        }
                        log.info("Confituration of cacheName {} is successfully",h2CacheRedisConfig.getCacheName());
                        cacheConfigurations.put(h2CacheRedisConfig.getCacheName(),tempConfig);
                    }
                }

                redisCacheManager = RedisCacheManager.builder(connectionFactory)
                        .cacheDefaults(defaultConfig)
                        .withInitialCacheConfigurations(cacheConfigurations)
                        .transactionAware()
                        .build();
                return redisCacheManager;
            }
            redisCacheManager = RedisCacheManager.builder(connectionFactory)
                    .cacheDefaults(defaultConfig)
                    .transactionAware()
                    .build();

            return redisCacheManager;
        }catch (Exception e){
            log.error("H2Cache load redis config error;{}",e.getMessage());
            throw new H2CacheRedisException(e);
        }

    }

    /**
     * key键序列化方式
     * @return
     */
    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    /**
     * value值序列化方式
     * @return
     */
    private GenericJackson2JsonRedisSerializer valueSerializer(){
        return new GenericJackson2JsonRedisSerializer();
    }
}

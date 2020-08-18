package com.hyf.cache.redis;

import com.hyf.cache.constant.Constant;
import com.hyf.cache.exception.H2CacheRedisException;
import com.hyf.cache.pojo.H2CacheRedisConfig;
import com.hyf.cache.pojo.H2CacheRedisDefault;
import com.hyf.cache.properties.H2CacheRedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
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
 * Redis Configuration
 * @author howinfun
 * @since 2020-04-01
 */
@Configuration
@ConditionalOnClass(RedisCache.class)
@ConditionalOnProperty(prefix = "h2cache", value = "enabled", havingValue = "true")
@Slf4j
public class H2CacheRedisConfiguration {

    @Resource
    private H2CacheRedisProperties properties;

    @Bean
    @ConditionalOnMissingBean(RedisCacheManager.class)
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) throws H2CacheRedisException {

        try {

            RedisCacheManager redisCacheManager;
            RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory);

            H2CacheRedisDefault redisDefault = properties.getDefaultConfig();
            RedisCacheConfiguration defaultConfig;
            if (null != redisDefault){
                defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(redisDefault.getTtl())
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()));
                if (redisDefault.getDisableNullValues()){
                    defaultConfig = defaultConfig.disableCachingNullValues();
                }
                if (!redisDefault.getUsePrefix()){
                    defaultConfig = defaultConfig.disableKeyPrefix();
                }
                log.info("H2CacheRedisManager : Configuration of default is successfully");
            }else {
                defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                        .disableCachingNullValues();
                log.info("H2CacheRedisManager : Configuration of default is successfully");
            }
            builder.cacheDefaults(defaultConfig);

            List<H2CacheRedisConfig> configList = properties.getConfigList();
            Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

            if (null != configList && configList.size() > 0){

                for (H2CacheRedisConfig h2CacheRedisConfig : configList) {
                    RedisCacheConfiguration tempConfig;
                    if (null != h2CacheRedisConfig.getCacheName() && !"".equals(h2CacheRedisConfig.getCacheName())){
                        tempConfig = RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofSeconds(h2CacheRedisConfig.getTtl()))
                                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()));
                        if (h2CacheRedisConfig.getDisableNullValues()){
                            tempConfig = tempConfig.disableCachingNullValues();
                        }
                        if (!h2CacheRedisConfig.getUsePrefix()){
                            tempConfig = tempConfig.disableKeyPrefix();
                        }
                        log.info("H2CacheRedisManager : Configuration of cacheName {} is successfully",h2CacheRedisConfig.getCacheName());
                        cacheConfigurations.put(h2CacheRedisConfig.getCacheName(),tempConfig);
                    }
                }
                builder.withInitialCacheConfigurations(cacheConfigurations);
            }
            redisCacheManager = builder.build();
            return redisCacheManager;
        }catch (Exception e){
            log.error("H2Cache load redis config error;{}",e.getMessage());
            throw new H2CacheRedisException(e);
        }

    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerCacheEvictAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerCacheEvictAdapter, new PatternTopic(Constant.H2CACHECACHEEVICTDISTRIBUTION));
        return container;
    }

    @Bean
    public RedisMessageReceiver template(EhCacheCacheManager ehCacheCacheManager) {
        RedisMessageReceiver redisMessageReceiver = new RedisMessageReceiver();
        redisMessageReceiver.setEhCacheCacheManager(ehCacheCacheManager);
        return redisMessageReceiver;
    }

    @Bean
    public MessageListenerAdapter listenerCacheEvictAdapter(RedisMessageReceiver redisMessageReceiver) {
        return new MessageListenerAdapter(redisMessageReceiver, "cacheEvictMessage");
    }

    @Bean
    public StringRedisTemplate redisMessageReceiver(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
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

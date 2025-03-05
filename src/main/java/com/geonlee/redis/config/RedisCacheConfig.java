package com.geonlee.redis.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Map;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        return RedisCacheManager
                .builder(redisConnectionFactory)
                /**
                 * RedisCacheWriter의 Lock기능을 기본적으로 사용하여 동시성 이슈를 방지하고 있었으나
                 * 명시적으로 해당 기능을 끌 수 있다.
                 *
                 * Bach Size또한 지정이 가능하며, 이런 기능은 성능향상에 도움을 줄 수 있다.
                 */
                // .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory, BatchStrategies.scan(1000)))
                /**
                 * Spring Data Redis에서 RedisCacheWriter를 사용하며 기본적으로 적용되는 설정
                 * Lock이 기본적으로 걸려있고, 해당 락은 LUA script를 사용하여 LOCK을 처리함
                 * 이미 사용중인 Process가 존재한다면 기다렸다 사용하는 구조
                 */
//                .builder(RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(generateCacheConfiguration())
                .build();
    }

    private RedisCacheConfiguration generateCacheConfiguration() {
        /**
         * 기본 cache TTL 5분 설정
         * Redis 6.2.0 GETEX Version이 아닐경우 error 발생
         */
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java()));
//                .entryTtl(Duration.ofMinutes(5))
//                .enableTimeToIdle();

        /**
         * 트러블 슈팅
         * Serialize 할 때 오류가 발생하면 Exception없이 정상작동 안되는 현상이 발생할 수 있음.
         */
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
}

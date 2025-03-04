package com.geonlee.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@Configuration
public class RedisCacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        /**
         * 기본 cache TTL 5분 설정
         * Redis 6.2.0 GETEX Version이 아닐경우 error 발생
         */
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .enableTimeToIdle();

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
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}

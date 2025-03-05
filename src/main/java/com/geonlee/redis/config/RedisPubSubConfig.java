package com.geonlee.redis.config;

import com.geonlee.redis.example.interceptors.RedisSubscribeListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;


@Configuration
@RequiredArgsConstructor
public class RedisPubSubConfig {
    //
    @Value("${spring.data.redis.topics}")
    private String[] topics;

    private final RedisSubscribeListener redisSubscribeListener;

    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer(@Qualifier("redisConnectionFactory") RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        for(String topic : topics) {
            container.addMessageListener(redisSubscribeListener, new ChannelTopic(topic));
        }
        return container;
    }
}


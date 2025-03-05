package com.geonlee.redis.example.interceptors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisSubscribeListener implements MessageListener {
    //
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("Redis Subscribe Topic: {}", redisTemplate.getStringSerializer().deserialize(message.getChannel()));
        log.info("Redis message: {}", redisTemplate.getStringSerializer().deserialize(message.getBody()));
    }
}

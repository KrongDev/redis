package com.geonlee.redis.example.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {
    //
    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    public void publish(ChannelTopic topic, String message) {
        reactiveRedisTemplate.convertAndSend(topic.getTopic(), message).subscribe();
    }

}

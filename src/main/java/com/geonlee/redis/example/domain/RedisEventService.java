package com.geonlee.redis.example.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisEventService {
    //
    private final RedisTemplate<String, Object> redisTemplate;

    public void send() {

    }
}

package com.geonlee.redis.example.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RedisStreamServiceTest {

    @Autowired
    private RedisStreamService redisStreamService;

    /**
     * Redis 5.0이상부터 테스트 가능 XADD명령어 가능
     */
    @Test
    void appendStreamData() {
        // GIVEN
        String streamName = "streamTest";
        Map<String, String> data = Map.of("test1", "test1Value", "test2", "test2Value");

        // WHEN
        String recordId = redisStreamService.appendStreamData(streamName, data);

        // THEN
        assertThat(recordId).isNotNull();
    }
}
package com.geonlee.redis.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ValueOperationsTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    @DisplayName("일반 문자열 저장 및 출력 테스트")
    public void valueOperations() {
        String key = "first";
        String value = "hello";
        redisTemplate.opsForValue().set(key, value);
        String redisValue = (String) redisTemplate.opsForValue().get(key);

        assertThat(value).isEqualTo(redisValue);
    }
}

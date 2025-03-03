package com.geonlee.redis.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@DisplayName("HashOperations를 활용한 Redis Test")
public class HashOperationsTest {
    private final String nameKey = "name";
    private final String ageKey = "age";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, String> hashOperations;

    @BeforeEach
    void setUp() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Test
    @DisplayName("Hash 자료형 저장 및 출력 테스트")
    void hashOperationsTest() {
        String key = "hash";
        String name = "geon lee";
        String age = "28";
        // GIVEN
        hashOperations.put(key, nameKey, name);
        hashOperations.put(key, ageKey, age);

        // WHEN
        String getName = hashOperations.get(key, nameKey);
        String getAge = hashOperations.get(key, ageKey);

        // THEN
        assertThat(name).isEqualTo(getName);
        assertThat(age).isEqualTo(getAge);
    }
}

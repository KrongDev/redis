package com.geonlee.redis.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("ZSetOperations를 활용한 Redis Test")
public class ZSetOperationsTest {
    //
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    @DisplayName("ZSet 자료형 저장 및 출력 테스트")
    public void setOperationsTest() {
        // GIVEN
        String key = "zset";
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key, "Alice", 1);
        zSetOperations.add(key, "Bob", 2);
        zSetOperations.add(key, "Charlie", 3);
        // WHEN - THEN
        Set<Object> tags = zSetOperations.reverseRange(key, 0, -1);
        assertThat(tags).containsExactly("Charlie", "Bob", "Alice");

        assertThat(zSetOperations.score(key, "Alice")).isEqualTo(1);

        zSetOperations.remove(key, "Alice");
        assertThat(zSetOperations.rank(key, "Alice")).isNull();
    }
}

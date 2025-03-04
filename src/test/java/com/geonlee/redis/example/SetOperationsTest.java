package com.geonlee.redis.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("SetOperations를 활용한 Redis Test")
public class SetOperationsTest {
    //
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    @DisplayName("Set 자료형 저장 및 출력 테스트")
    public void setOperationsTest() {
        // GIVEN
        String key = "set";
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        setOperations.add(key, "Java", "Spring", "Redis", "Spring");
        // WHEN - THEN
        Set<Object> tags = setOperations.members(key);
        assertThat(tags).containsExactlyInAnyOrder("Java", "Spring", "Redis");

        assertThat(setOperations.isMember(key, "Spring")).isTrue();

        setOperations.remove(key, "Redis");
        assertThat(setOperations.members(key)).doesNotContain("Redis");
    }
}

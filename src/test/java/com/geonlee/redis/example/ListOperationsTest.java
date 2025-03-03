package com.geonlee.redis.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@DisplayName("ListOperations를 활용한 Redis Test")
public class ListOperationsTest {
    //
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    @DisplayName("List 자료형 저장 및 출력 테스트")
    public void listOperationsTest() {
        // GIVEN
        String key = "list";
        String[] arr = {"Task 1", "Task 2", "Task 3"};
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(key, arr[0]);
        listOperations.rightPush(key, arr[1]);
        listOperations.rightPush(key, arr[2]);
        // WHEN - THEN
        for (String s : arr) {
            assertThat(listOperations.leftPop(key)).isEqualTo(s);
        }
    }
}

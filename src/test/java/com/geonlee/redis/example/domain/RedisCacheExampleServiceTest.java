package com.geonlee.redis.example.domain;

import com.geonlee.redis.example.domain.aggregate.Example;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RedisCacheExampleServiceTest {
    @Autowired
    private RedisCacheExampleService redisCacheExampleService;

    @Test
    void exampleCacheService() {
        //GIVEN
        String name = "test1";
        String description = "testData";
        String updateDescription = "updateDescription";
        Example example = redisCacheExampleService.create(name, description);
        // WHEN
        example = redisCacheExampleService.retrieve(example.getId());
        example = redisCacheExampleService.retrieve(example.getId());
        example = redisCacheExampleService.retrieve(example.getId());
        redisCacheExampleService.update(example.getId(), name, updateDescription);
        // THEN
        example = redisCacheExampleService.retrieve(example.getId());
        assertThat(example.getDescription()).isEqualTo(updateDescription);
    }
}
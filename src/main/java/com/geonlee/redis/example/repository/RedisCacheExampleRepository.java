package com.geonlee.redis.example.repository;

import com.geonlee.redis.example.domain.aggregate.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedisCacheExampleRepository extends JpaRepository<Example, Long> {
}

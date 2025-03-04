package com.geonlee.redis.example.domain;

import com.geonlee.redis.example.domain.aggregate.Example;
import com.geonlee.redis.example.repository.RedisCacheExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RedisCacheExampleService {
    //
    private final RedisCacheExampleRepository redisCacheExampleRepository;

    @Transactional
    public Example create(String name, String description) {
           return redisCacheExampleRepository.save(Example.of(name, description));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "example", key = "#exampleId", cacheManager = "cacheManager")
    public Example retrieve(Long exampleId) {
        return redisCacheExampleRepository.findById(exampleId)
                .orElseThrow();
    }

    @Transactional
    @CacheEvict(value = "example", key = "#exampleId", cacheManager = "cacheManager")
    public void update(Long exampleId, String name, String description) {
        Example example = retrieve(exampleId);
        example.update(name, description);
    }

    @Transactional
    @CacheEvict(value = "example", key = "#exampleId", cacheManager = "cacheManager")
    public void delete(Long exampleId) {
        redisCacheExampleRepository.deleteById(exampleId);
    }
}

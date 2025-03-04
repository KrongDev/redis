package com.geonlee.redis.example.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RedisStreamService {
    //
    private final RedisConnection redisConnection;

    public String appendStreamData(String streamName, Map<String, String> data) {
        Map<byte[], byte[]> byteData = new HashMap<>();
        data.forEach((key, value) -> byteData.put(key.getBytes(), value.getBytes()));

        RecordId recordId = redisConnection.xAdd(streamName.getBytes(), byteData);
        return Objects.isNull(recordId) ? null : recordId.toString();
    }
}
